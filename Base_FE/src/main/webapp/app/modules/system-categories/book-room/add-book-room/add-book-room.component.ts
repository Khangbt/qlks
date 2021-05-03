import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HeightService } from 'app/shared/services/height.service';
import { ToastService } from 'app/shared/services/toast.service';

@Component({
  selector: 'jhi-add-book-room',
  templateUrl: './add-book-room.component.html',
  styleUrls: ['./add-book-room.component.scss']
})
export class AddBookRoomComponent implements OnInit {
  @Input() type;
  @Input() id: any;
  @Output() passEntry: EventEmitter<any> = new EventEmitter();
  @Input() partId: any;
  @Input() status: any;
  form: FormGroup;
  height: number;
  title: String;
  listServiceR = [];
  checkNul = [];
  listServiceDefule = [];
  unitList = [];
  dataIdListConvet = [];

  constructor(
    private heightService: HeightService,
    private formBuilder: FormBuilder,
    private toastService: ToastService,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.buildForm();
  }

  private buildForm() {
    if (this.type === 'add') {
      this.title = 'Tạp phiếu mượn';
    } else if (this.type === 'update') {
      this.title = 'Sửa phiếu yêu cầu ';
    } else this.title = 'Xem chi tiết phiếu yêu cầu';
    this.form = this.formBuilder.group({
      id: [this.id],
      code: [],
      status: [1],
      note: [],
      nameHummer: [],
      approvedDate: [],
      listService: this.formBuilder.array([]),
      reason: []
    });

    if (this.id) {
      this.getDetail(this.id);
    } else {
      this.form.get('creatDate').setValue(new Date());
    }
  }
  getDetail(id) {}
  get formControl() {
    return this.form.controls;
  }

  onResize() {
    this.height = this.heightService.onResizeWithoutFooter();
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
  setValueToField(item, data) {
    this.form.get(item).setValue(data);
  }

  getValueOfField(item) {
    return this.form.get(item).value;
  }
  onCancel() {
    this.activeModal.dismiss();
  }
  onCancelModal(conten) {}
  onSubmit() {}
  saveDevice(i) {
    //check dk search
    // if(this.form.get("listService").value[i].idGroup==null){
    //   this.toastService.openErrorToast('Hãy chọn loại thiết bị yêu cầu');
    //   return
    // }
    // if(this.form.get("listService").value[i].quantity<=0){
    //   this.toastService.openErrorToast('Hãy chọn số lượng thiết bị yêu cầu');
    //   return
    //  }
    this.checkNul[i] = !this.checkNul[i];
    this.xetTrueFal(this.form.get('listService').value);
  }
  xetTrueFal(data1) {
    console.warn(data1);
    if (data1.length === 0) {
      for (const p of this.listServiceDefule) {
        p.disabled = false;
      }
    } else {
      for (const p of this.listServiceDefule) {
        p.disabled = false;
      }
      for (const c of data1) {
        for (const p of this.listServiceDefule) {
          if (p.id === c.idGroup) {
            p.disabled = true;
          }
        }
      }
      console.warn(this.listServiceDefule);
    }
  }

  addService(value) {
    for (const t of this.checkNul) {
      if (!t) {
        return;
      }
      this.onResize();
    }

    this.checkNul.push(value);
    // eslint-disable-next-line @typescript-eslint/consistent-type-assertions
    (<FormArray>this.form.get('listService')).push(
      this.formBuilder.group({
        size: [],
        unit: [],
        quantity: [0],
        id: []
      })
    );
    this.listServiceR = this.form.get('listService').value;
  }
  deleteDevice(i) {
    this.checkNul.splice(i, 1);

    // eslint-disable-next-line @typescript-eslint/consistent-type-assertions
    (<FormArray>this.form.get('listService')).removeAt(i);
    this.listServiceR = this.form.get('listService').value;
    this.xetTrueFal(this.form.get('listService').value);
  }
  xetDataDefile(event, i) {
    if (event !== undefined) {
      this.listServiceR[i].unit = event.unit ? this.xetUntit(event.unit) : null;
      this.listServiceR[i].idGroup = event.id;
      this.listServiceR[i].size = event.sizeWarhous ? event.sizeWarhous : null;
      this.listServiceR[i].unit = event.unit ? event.unit : null;

      this.form.controls['listDevice'].setValue(this.listServiceR);
      this.xetTrueFal(this.form.controls['listDevice'].value);
    } else {
      this.listServiceR[i].unit = null;
      this.listServiceR[i].idGroup = null;
      this.listServiceR[i].size = null;

      this.form.controls['listDevice'].setValue(this.listServiceR);
    }
  }

  xetUntit(unit) {
    const c = this.unitList.filter(function(value) {
      return value.id === unit;
    });
    console.warn(c);
    console.warn(unit);
    return c[0].name;
  }
}
