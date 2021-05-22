import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup } from '@angular/forms';
import { HeightService } from 'app/shared/services/height.service';

@Component({
  selector: 'jhi-pay',
  templateUrl: './pay.component.html',
  styleUrls: ['./pay.component.scss']
})
export class PayComponent implements OnInit {
  @Input() type;
  @Input() id: any;
  @Output() passEntry: EventEmitter<any> = new EventEmitter();
  ngbModalRef: NgbModalRef;
  form: FormGroup;
  height: number;
  maxlength = 4;
  constructor(public activeModal: NgbActiveModal, private heightService: HeightService) {
    this.height = this.heightService.onResize();
  }

  ngOnInit() {}

  onCancel() {
    this.activeModal.dismiss();
  }
  onpay() {}
}
