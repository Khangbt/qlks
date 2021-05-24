import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HeightService } from 'app/shared/services/height.service';
import { ConfirmModalComponent } from 'app/shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'jhi-pay',
  templateUrl: './pay.component.html',
  styleUrls: ['./pay.component.scss']
})
export class PayComponent implements OnInit {
  oldEmail: any;
  @Input() type;
  @Input() id: any;
  @Input() bookType: any;
  @Input() bookingRoomId: any;
  @Input() bookingId: any;
  @Output() passEntry: EventEmitter<any> = new EventEmitter();
  ngbModalRef: NgbModalRef;
  form: FormGroup;
  height: number;
  maxlength = 4;
  constructor(
    public activeModal: NgbActiveModal,
    private heightService: HeightService,
    private modalService: NgbModal,
    private formBuilder: FormBuilder
  ) {
    this.height = this.heightService.onResize();
  }

  ngOnInit() {
    this.buildForm();
  }
  onPay() {}
  onInvoicePrint() {}

  private buildForm() {
    this.form = this.formBuilder.group({
      bookingroomId: []
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
  onResize() {
    this.height = this.heightService.onResize();
  }
}
