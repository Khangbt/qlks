import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HeightService } from 'app/shared/services/height.service';
import { CommonService } from 'app/shared/services/common.service';
import { ToastService } from 'app/shared/services/toast.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { JhiEventManager } from 'ng-jhipster';
import { SysUserService } from 'app/core/services/system-management/sys-user.service';
import { RoomApiServiceService } from 'app/core/services/room-api/room-api-service.service';
import { TranslateService } from '@ngx-translate/core';
import { DatePipe } from '@angular/common';
import { FormStoringService } from 'app/shared/services/form-storing.service';
import { Router } from '@angular/router';
import { STORAGE_KEYS } from 'app/shared/constants/storage-keys.constants';
import { CustomerApiService } from 'app/core/services/customer-api/customer-api.service.service';

@Component({
  selector: 'jhi-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.scss']
})
export class AddCustomerComponent implements OnInit {
  oldEmail: any;
  @Input() type;
  @Input() id: any;
  @Output() passEntry: EventEmitter<any> = new EventEmitter();
  ngbModalRef: NgbModalRef;
  form: FormGroup;
  height: number;
  maxlength = 4;
  isDuplicateUserCode = false;
  title = '';
  checkBoll = false;
  isYear = false;
  yy: number;
  years: number[] = [];
  userDetail: any;
  post: Date;
  assetList: any[] = [];
  roomTypeList: any[] = [];
  roomFloorList: any[] = [];
  ////////////////////////
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
    private customerApiService: CustomerApiService,
    private translateService: TranslateService,
    private datepipe: DatePipe,
    private formStoringService: FormStoringService,
    protected router: Router
  ) {
    this.height = this.heightService.onResize();
  }

  get formControl() {
    return this.form.controls;
  }

  ngOnInit() {
    this.buildForm();
  }

  /////////////////////////////////////////////////
  onSubmitData() {
    if (this.form.invalid) {
      this.commonService.validateAllFormFields(this.form);
      console.warn('aaaaaaa');
      return;
    }

    if (this.type === 'add') {
      if (this.isDuplicateUserCode) {
        console.warn('duplicate');
        return;
      }
    }
    this.spinner.show();
    this.customerApiService.save(this.form.value).subscribe(
      res => {
        if (this.type === 'add') {
          this.toastService.openSuccessToast('Th??m m???i th??nh c??ng !');
        }
        if (this.type === 'update') {
          this.toastService.openSuccessToast('S???a th??nh c??ng !');
        }

        this.router.navigate(['system-categories/customer-resources']);
        this.activeModal.dismiss();
      },
      err => {
        this.toastService.openErrorToast(this.type === 'add' ? 'Th??m m???i kh??ng th??nh c??ng' : 'S???a th???t b???i');
        this.spinner.hide();
      },
      () => {
        this.spinner.hide();
      }
    );
  }

  trimSpace(element) {
    const value = this.getValueOfField(element);
    if (value) {
      this.setValueToField(element, value.trim());
    }
  }

  onBlurUserCode() {
    // if (this.type === 'add'&& this.userDetail.code !== this.form.value.code) {
    // if (this.type === 'add') {
    //
    //   this.humanResourceService.checkUserCode(this.form.value.code).subscribe(res => {
    //
    //     this.isDuplicateUserCode = false;
    //
    //   }, err => {
    //     this.isDuplicateUserCode = true;
    //   });
    // }
  }

  displayFieldHasError(field: string) {
    return {
      'has-error': this.isFieldValid(field)
    };
  }

  isFieldValid(field: string) {
    return !this.form.get(field).valid && this.form.get(field).touched;
  }

  getValueOfField(item) {
    return this.form.get(item).value;
  }

  setValueToField(item, data) {
    this.form.get(item).setValue(data);
  }

  onResize() {
    this.height = this.heightService.onResizeWithoutFooter();
  }

  onCancel() {
    this.activeModal.dismiss();

    // if (this.type === 'update') {
    //   if (
    //     this.form.value.humanResourceId === this.userDetail.humanResourceId &&
    //     this.form.value.code === this.userDetail.code &&
    //     this.form.value.email === this.userDetail.email &&
    //     this.form.value.fullName === this.userDetail.fullName &&
    //     this.form.value.partId === this.userDetail.partId &&
    //     this.form.value.status === this.userDetail.status &&
    //     this.form.value.majorId === this.userDetail.majorId &&
    //     this.form.value.dateMajor === this.userDetail.dateMajor &&
    //     this.form.value.note === this.userDetail.note
    //   ) {
    //     this.activeModal.dismiss();
    //   } else {
    //     const modalRef = this.modalService.open(ConfirmModalComponent, {centered: true, backdrop: 'static'});
    //     modalRef.componentInstance.type = 'confirm';
    //     modalRef.componentInstance.onCloseModal.subscribe(value => {
    //       if (value === true) {
    //         this.activeModal.dismiss();
    //       }
    //     });
    //   }
    // }
    // if (this.type === 'add') {
    //   if (
    //     this.form.value.humanResourceId === null &&
    //     this.form.value.code === '' &&
    //     this.form.value.email === '' &&
    //     this.form.value.fullName === '' &&
    //     this.form.value.positionId === null &&
    //     this.checkNull() &&
    //     this.form.value.status === this.statusList[0].id &&
    //     this.form.value.note === ''
    //
    //   ) {
    //     this.activeModal.dismiss();
    //   } else {
    //     const modalRef = this.modalService.open(ConfirmModalComponent, {centered: true, backdrop: 'static'});
    //     modalRef.componentInstance.type = 'confirm';
    //     modalRef.componentInstance.onCloseModal.subscribe(value => {
    //       if (value === true) {
    //         this.activeModal.dismiss();
    //       }
    //     });
    //   }
    // }
    // if (this.type === 'detail') {
    //   this.activeModal.dismiss();
    // }
  }

  getUserDetail(id) {
    this.customerApiService.getCustomer(id).subscribe(
      res => {
        this.userDetail = res.data;

        this.oldEmail = this.userDetail.email ? this.userDetail.email : '';

        this.setDataDefault();
      },
      err => {
        this.userDetail = null;
      }
    );
  }

  setDataDefault() {
    this.form.patchValue(this.userDetail);
    this.post = new Date(this.userDetail);
    // if (this.userDetail.dateGraduate) {
    //   this.form.get('experience').setValue(new Date().getFullYear() - toNumber(this.userDetail.dateGraduate));
    // }
    // if (this.userDetail.dateMajor) {
    //   this.form.get('majorExperience').setValue(new Date().getFullYear() - toNumber(this.userDetail.dateMajor));
    // }
  }

  getYear() {
    const todays = new Date();
    this.yy = todays.getFullYear();
    for (let i = this.yy; i >= 1970; i--) {
      this.years.push(i);
    }
  }

  xetDataUer() {
    const userToken: any = this.formStoringService.get(STORAGE_KEYS.USER);
    if (userToken.role === 'ROLE_ADMINPART') {
      this.form.get('partId').setValue(userToken.assetId);
      this.form.get('roomTypeId').setValue(userToken.roomType);
      this.form.get('id').setValue(userToken.roomType);

      this.checkBoll = true;
    } else {
      this.checkBoll = false;
    }
  }

  private buildForm() {
    if (this.type === 'add') {
      this.title = 'Th??m m???i kh??ch h??ng';
    } else if (this.type === 'update') {
      this.title = 'S???a th??ng tin kh??ch h??ng';
    } else this.title = 'Xem chi ti???t kh??ch h??ng';

    this.form = this.formBuilder.group({
      customerId: null,
      cmt: null,
      // roomCode: ['', Validators.compose([Validators.required, Validators.maxLength(50), Validators.pattern(/^[a-zA-Z0-9]+$/)])],
      fullname: ['', Validators.compose([Validators.required, Validators.maxLength(255)])],
      status: 1,
      phoneNumber: null,
      email: null,
      address: null
    });
    if (this.id) {
      this.getUserDetail(this.id);
      this.xetDataUer();
    } else {
      this.xetDataUer();
    }
    this.getYear();
  }
}
