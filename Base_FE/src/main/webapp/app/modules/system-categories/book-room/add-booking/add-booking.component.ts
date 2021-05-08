import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DatePipe } from '@angular/common';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, of, Subject } from 'rxjs';
import { HeightService } from 'app/shared/services/height.service';
import { CommonService } from 'app/shared/services/common.service';
import { ToastService } from 'app/shared/services/toast.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { JhiEventManager } from 'ng-jhipster';
import { SysUserService } from 'app/core/services/system-management/sys-user.service';
import { TranslateService } from '@ngx-translate/core';
import { FormStoringService } from 'app/shared/services/form-storing.service';
import { Router } from '@angular/router';
import { RoomApiServiceService } from 'app/core/services/room-api/room-api-service.service';
import { RoomTypeApiServiceService } from 'app/core/services/room-type/room-type-api-service.service';
import { CustomerApiService } from 'app/core/services/customer-api/customer-api.service.service';
import { PromotionService } from 'app/core/services/promotionService/promotion.service';
import { BookingRoomApi } from 'app/core/services/booking-room-api/booking-room-api';
import { debounceTime } from 'rxjs/operators';
import { TIME_OUT } from 'app/shared/constants/set-timeout.constants';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HttpResponse } from '@angular/common/http';
import { STATUS_CODE } from 'app/shared/constants/status-code.constants';
import { ConfirmModalComponent } from 'app/shared/components/confirm-modal/confirm-modal.component';
import { STORAGE_KEYS } from 'app/shared/constants/storage-keys.constants';

@Component({
  selector: 'jhi-add-booking',
  templateUrl: './add-booking.component.html',
  styleUrls: ['./add-booking.component.scss'],
  providers: [DatePipe]
})
export class AddBookingComponent implements OnInit {
  oldEmail: any;
  @Input() type;
  @Input() id: any;
  @Input() bookType: any;
  @Output() passEntry: EventEmitter<any> = new EventEmitter();
  ngbModalRef: NgbModalRef;
  form: FormGroup;
  listUnit$ = new Observable<any[]>();
  unitSearch;
  roomType;
  giaPhong;
  maKM;
  tienThuePhong;
  tienKhuyenMai;
  idLoaiPhong;
  daThanhToan;
  idPhong;
  loaiDatPhong;
  tienDV;
  activeNgayDat;
  activeNgayDuKienDi;
  activeNgayDen;
  activeNgayTra;
  tongTien;
  debouncer: Subject<string> = new Subject<string>();
  //relase
  listRoom = [];
  listCustomer = [];
  listPromotion = [];
  listBookingType = [
    {
      id: 1,
      name: 'Theo giờ'
    },
    {
      id: 2,
      name: 'Theo ngày'
    },
    {
      id: 3,
      name: 'Qua đêm'
    }
  ];
  //end relase
  roleList: any[] = [];
  height: number;
  post: Date;
  name: string;
  maxlength = 4;
  isError = false;
  userDetail: any;
  isYear = false;
  yy: number;
  years: number[] = [];
  checkBoll = false;
  statusList = [
    {
      id: 1,
      status: 'Chưa thanh toán'
    },
    {
      id: 2,
      status: 'Đã thanh toán'
    }
  ];
  title = '';
  constructor(
    public activeModal: NgbActiveModal,
    private heightService: HeightService,
    private formBuilder: FormBuilder,
    private commonService: CommonService,
    private toastService: ToastService,
    private spinner: NgxSpinnerService,
    private modalService: NgbModal,
    private eventManager: JhiEventManager,
    private sysUserService: SysUserService,
    private translateService: TranslateService,
    private datepipe: DatePipe,
    private roomApiService: RoomApiServiceService,
    private customerApiService: CustomerApiService,
    private formStoringService: FormStoringService,
    private roomTypeApiService: RoomTypeApiServiceService,
    private bookingRoomApi: BookingRoomApi,
    private promotionService: PromotionService,
    protected router: Router
  ) {
    this.height = this.heightService.onResize();
  }

  get formControl() {
    return this.form.controls;
  }

  ngOnInit() {
    this.getDefaultData();
    this.buildForm();
    this.debounceOnSearch();
    this.getRoleList();
    this.getRoomList();
    this.getListCustomer();
    this.setValueToField('bookingDate', new Date());
  }

  checkActiveMaPhong() {
    if (this.bookType === 'current') {
      return this.type !== 'foward';
    } else if (this.bookType === 'future') {
      return this.type !== 'add' && this.type !== 'update';
    }
  }

  getPromotion() {
    this.maKM = this.getValueOfField('promotionCode');
    if (this.checkNullOrEmpty(this.maKM) && this.checkNullOrEmpty(this.idLoaiPhong)) {
      this.promotionService.getByCodeAndRoomType(this.maKM, this.idLoaiPhong).subscribe(
        res => {
          if (res) {
            if (this.checkNullOrEmpty(res.data)) {
              if (this.checkNullOrEmpty(this.giaPhong)) {
                const phanTramKm = res.data.percentPromotion;
                if (phanTramKm > 0) {
                  this.setValueToField('priceBooking', (this.giaPhong * (100 - phanTramKm)) / 100);
                  this.tienThuePhong = (this.giaPhong * (100 - phanTramKm)) / 100;
                }
              }
            } else {
              this.setValueToField('priceBooking', this.giaPhong);
            }
          }
        },
        err => {
          this.toastService.openErrorToast('Error from server');
        }
      );
    } else {
      this.setValueToField('priceBooking', this.giaPhong);
    }
    this.tinhTongTien();
  }

  getListCustomer() {
    this.customerApiService.getAllCustomer().subscribe(
      res => {
        if (res) {
          this.listCustomer = res.data;
        } else {
          this.listCustomer = [];
        }
      },
      err => {
        this.toastService.openErrorToast('Error from server');
      }
    );
  }

  getPromotionList(roomType) {
    this.promotionService.getByRoomType(roomType).subscribe(
      res => {
        if (res) {
          this.listPromotion = res.data;
        } else {
          this.listPromotion = [];
        }
      },
      err => {
        this.listPromotion = [];
        this.toastService.openErrorToast('Server Error');
      }
    );
  }

  getRoomList() {
    this.roomApiService.getAll().subscribe(
      res => {
        if (res) {
          this.listRoom = res.data;
        } else {
          this.listRoom = [];
        }
      },
      err => {
        this.listRoom = [];
      }
    );
  }

  tinhTongTien() {
    this.daThanhToan = this.getValueOfField('advanceAmount') ? this.getValueOfField('advanceAmount') : 0;
    this.tienDV = this.getValueOfField('priceService') ? this.getValueOfField('priceService') : 0;
    this.tongTien = this.tienThuePhong + this.tienDV - this.daThanhToan;
    this.setValueToField('priceTotal', this.tongTien);
  }

  onCheckValidDateTime() {}

  getDataOnSelectBookType() {
    this.loaiDatPhong = this.getValueOfField('bookingType');
    if (this.checkNullOrEmpty(this.idPhong) && this.checkNullOrEmpty(this.loaiDatPhong)) {
      this.setGiaPhong(this.idPhong, this.loaiDatPhong);
    }
    this.getPromotion();
  }

  private getRoleList() {
    this.roleList = [{ id: 1, name: 'USER' }, { id: 2, name: 'ADMIN' }];
  }

  private buildForm() {
    this.form = this.formBuilder.group({
      roomCode: [],
      price: [],
      customerId: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      bookingType: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      totalDate: [],
      priceBooking: [],
      priceService: [],
      advanceAmount: [],
      priceTotal: [],
      bookingDate: ['', Validators.compose([Validators.required])],
      bookingDateOut: ['', Validators.compose([Validators.required])],
      bookingCheckin: [],
      bookingCheckout: [],
      status: this.statusList[0].id,
      roomId: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      promotionCode: [],
      note: ['', Validators.maxLength(1000)]
    });
    if (this.id) {
      this.getUserDetail(this.id);
      this.xetDataUer();
    } else {
      this.xetDataUer();
    }
    this.getYear();
  }

  getDefaultData() {
    if (this.type && this.id) {
      this.commonService.clearDataTranfer('type');
      this.commonService.clearDataTranfer('id');
    } else {
      if (this.type !== 'add') {
        if (this.bookType === 'current') {
          this.router.navigate(['system-categories/book-room']);
        } else {
          this.router.navigate(['system-categories/book-room-future']);
        }
      }
    }
  }

  setDataDefault() {
    this.form.patchValue(this.userDetail);
    this.post = new Date(this.userDetail);
  }

  checkNullOrEmpty(item) {
    if (item !== null && item !== '' && item !== undefined) {
      return true;
    }
    return false;
  }

  setGiaPhong(loaiPhong, loaiDatPhong) {
    if (this.checkNullOrEmpty(loaiPhong) && this.checkNullOrEmpty(loaiDatPhong)) {
      this.roomTypeApiService.getByIdAndType(loaiPhong, loaiDatPhong).subscribe(
        res => {
          this.setValueToField('price', res.data.price);
          this.setValueToField('priceBooking', res.data.price);
          this.giaPhong = res.data.price;
          this.tienThuePhong = res.data.price;
        },
        error => {
          this.toastService.openErrorToast('Error from server');
        }
      );
    }
  }

  getDataOnSelectRoom(event) {
    this.getUserDetail(event.roomId);
  }

  getUserDetail(id) {
    this.idPhong = id;
    this.loaiDatPhong = this.getValueOfField('bookingType') ? this.getValueOfField('bookingType') : 0;
    this.roomApiService.getInfo(this.idPhong).subscribe(
      res => {
        if (res) {
          const d: any = res;
          this.idLoaiPhong = d.data.roomType;
          this.setValueToField('roomId', res.data.roomId);
          this.setGiaPhong(this.idLoaiPhong, this.loaiDatPhong);
          this.getPromotionList(this.idLoaiPhong);
        }
      },
      err => {
        this.toastService.openErrorToast('Error from server');
      }
    );
  }

  debounceOnSearch() {
    this.debouncer.pipe(debounceTime(TIME_OUT.DUE_TIME_SEARCH)).subscribe(value => this.loadDataOnSearchUnit(value));
  }

  loadDataOnSearchUnit(term) {
    this.sysUserService
      .getUnit({
        name: term,
        limit: ITEMS_PER_PAGE,
        page: 0
      })
      .subscribe((res: HttpResponse<any[]>) => {
        if (res && res.status === STATUS_CODE.SUCCESS && this.unitSearch) {
          this.listUnit$ = of(res.body['content'].sort((a, b) => a.name.localeCompare(b.name)));
        } else {
          this.listUnit$ = of([]);
        }
      });
  }

  onCancel() {
    if (this.type !== 'detail') {
      const modalRef = this.modalService.open(ConfirmModalComponent, { centered: true, backdrop: 'static' });
      modalRef.componentInstance.type = 'confirm';
      modalRef.componentInstance.onCloseModal.subscribe(value => {
        if (value === true) {
          this.activeModal.dismiss();
        }
      });
    }
    if (this.type === 'detail') {
      this.activeModal.dismiss();
    }
  }

  formatDate(date) {
    return new Date(date);
  }

  onSubmitData() {
    if (this.form.invalid) {
      this.commonService.validateAllFormFields(this.form);
      return;
    }
    this.form.get('bookingDate').setValue(new Date(this.form.get('bookingDate').value));
    this.form.get('bookingDateOut').setValue(new Date(this.form.get('bookingDateOut').value));
    this.form.get('bookingCheckin').setValue(new Date(this.form.get('bookingCheckin').value));
    this.form.get('bookingCheckout').setValue(new Date(this.form.get('bookingCheckout').value));
    this.spinner.show();
    this.bookingRoomApi.save(this.form.value).subscribe(
      res => {
        if (this.type === 'add') {
          this.toastService.openSuccessToast('Thêm mới thành công !');
        }
        if (this.type === 'update') {
          if (this.oldEmail !== this.form.value.email) {
            this.toastService.openSuccessToast('Thông tin đăng nhập mới đã được gửi về địa chỉ email ' + this.form.value.email);
          } else {
            this.toastService.openSuccessToast('Sửa thành công !');
          }
        }
        this.router.navigate(['system-categories/book-room']);
        this.activeModal.dismiss();
      },
      err => {
        this.toastService.openErrorToast(this.type === 'add' ? 'Thêm mới không thành công' : 'Sửa thất bại');
        this.spinner.hide();
      },
      () => {
        this.spinner.hide();
      }
    );
  }

  onPay() {}

  onInvoicePrint() {}

  viewChangeStr(item) {
    return '';
  }

  convertJson(value) {
    if (value) {
      return JSON.parse(value);
    }
  }

  getYear() {
    const todays = new Date();
    this.yy = todays.getFullYear();
    for (let i = this.yy; i >= 1970; i--) {
      this.years.push(i);
    }
  }

  onResize() {
    this.height = this.heightService.onResize();
  }

  displayFieldHasError(field: string) {
    return {
      'has-error': this.isFieldValid(field)
    };
  }

  isFieldValid(field: string) {
    return !this.form.get(field).valid && this.form.get(field).touched;
  }

  trimSpace(element) {
    const value = this.getValueOfField(element);
    if (value) {
      this.setValueToField(element, value.trim());
    }
  }

  getValueOfField(item) {
    return this.form.get(item).value;
  }

  setValueToField(item, data) {
    this.form.get(item).setValue(data);
  }

  xetDataUer() {
    const userToken: any = this.formStoringService.get(STORAGE_KEYS.USER);
    if (userToken.role === 'ROLE_ADMINPART') {
      this.form.get('partId').setValue(userToken.partId);
      this.checkBoll = true;
    } else {
      this.checkBoll = false;
    }
  }
  checkNull() {
    if (this.checkBoll) {
      return true;
    }
    if (this.form.value.partId === null && !this.checkBoll) {
      return true;
    }
    return false;
  }
}
