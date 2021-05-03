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
  @Output() passEntry: EventEmitter<any> = new EventEmitter();
  ngbModalRef: NgbModalRef;
  form: FormGroup;
  listUnit$ = new Observable<any[]>();
  unitSearch;
  roomType;
  debouncer: Subject<string> = new Subject<string>();
  //relase
  listRoom = [];
  listCustomer = [];
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
        this.listCustomer = [];
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

  onCheckValidDateTime() {}

  getRoomById(id) {
    this.roomType = this.getValueOfField('bookingType');
    var roomId = this.getValueOfField('roomId');
    this.roomApiService.getInfo(roomId).subscribe(
      res => {
        if (res) {
          const d: any = res;
          console.warn(d.data.roomType);
          this.roomTypeApiService.getByIdAndType(d.data.roomType, this.roomType).subscribe(res2 => {
            this.setValueToField('price', res2.data.price);
          });
        }
      },
      err => {
        this.toastService.openErrorToast('Error from server');
      }
    );
  }

  getDataOnSelectRoom(event) {
    this.getRoomById(event.roomId);
  }

  getDataOnSelectBookType(event) {
    this.getRoomById(event.roomId);
  }

  private getRoleList() {
    this.roleList = [{ id: 1, name: 'USER' }, { id: 2, name: 'ADMIN' }];
  }

  private buildForm() {
    this.form = this.formBuilder.group({
      roomCode: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      price: [],
      customerId: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      bookingType: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      totalDate: [],
      priceBooking: [],
      priceService: [],
      priceAdvance: [],
      priceTotal: [],
      bookingDate: [],
      bookingDateOut: [],
      bookingCheckin: [],
      bookingCheckout: [],
      status: this.statusList[0].id,
      roomId: [],
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
        this.router.navigate(['system-categories/human-resources']);
      }
    }
  }

  setDataDefault() {
    this.form.patchValue(this.userDetail);
    this.post = new Date(this.userDetail);
  }

  getUserDetail(id) {
    this.roomType = this.getValueOfField('bookingType') ? this.getValueOfField('bookingType') : 0;
    this.roomApiService.getInfo(id).subscribe(
      res => {
        if (res) {
          const d: any = res;
          this.setValueToField('roomId', res.data.roomId);
          this.roomTypeApiService.getByIdAndType(d.data.roomType, this.roomType).subscribe(res2 => {
            this.setValueToField('price', res2.data.price);
          });
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

  onSubmitData() {
    if (this.form.invalid) {
      this.commonService.validateAllFormFields(this.form);
      return;
    }
    this.form.get('dateOfBirth').setValue(new Date(this.form.get('dateOfBirth').value));
    this.spinner.show();
    // this.form.value.dateRecruitment = this.datepipe.transform(this.form.value.dateRecruitment, 'yyyy-MM-dd');
    /*this.humanResourceService.save(this.form.value).subscribe(
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
        this.router.navigate(['system-categories/human-resources']);
        this.activeModal.dismiss();
      },
      err => {
        this.toastService.openErrorToast(this.type === 'add' ? 'Thêm mới không thành công' : 'Sửa thất bại');
        this.spinner.hide();
      },
      () => {
        this.spinner.hide();
      }
    );*/
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

  binDataUsername(email) {
    if (this.form.get('email').valid) {
      if (email.includes('.iist@gmail.com')) {
        this.form.value.username = email.slice(0, email.indexOf('.iist@gmail.com'));
      } else if (email.includes('@iist.vn')) {
        this.form.value.username = email.slice(0, email.indexOf('@iist.vn'));
      } else {
        this.form.value.username = email.slice(0, email.indexOf('@iist.com.vn'));
      }
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

  checkKM() {
    var total = this.getValueOfField('price') ? this.getValueOfField('price') : 0;
    this.setValueToField('priceBooking', (total * 10) / 100);
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
