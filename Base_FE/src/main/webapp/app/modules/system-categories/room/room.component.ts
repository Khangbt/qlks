import { Component, OnInit } from '@angular/core';
import { AssetModel } from 'app/core/models/asset-model/asset-model';
import { FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { HeightService } from 'app/shared/services/height.service';
import { AddRoomComponent } from 'app/modules/system-categories/room/add-room/add-room.component';

@Component({
  selector: 'jhi-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {
  listAseetResourcse: AssetModel[] = [];
  columns = [
    { key: 0, value: 'Mã tài sản', isShow: true },
    { key: 1, value: 'Tên tài sản', isShow: true },
    // {key: 2, value: "Chức danh", isShow: true},
    { key: 2, value: 'Bộ phận', isShow: false },
    { key: 3, value: 'Trạng thái', isShow: true },
    // {key: 5, value: "Thời gian tốt nghiệp", isShow: false},
    { key: 4, value: 'Email', isShow: true },
    { key: 5, value: 'Số điện thoại', isShow: false },
    { key: 6, value: 'Ngày Sinh', isShow: false },
    { key: 7, value: 'Ghi chú', isShow: false }
  ];
  form: FormGroup;
  height: number;
  itemsPerPage: any;
  maxSizePage: any;
  routeData: any;
  page: number;
  second: any;
  totalItems: any;
  previousPage: any;
  predicate: any;
  reverse: any;
  searchForm: any;

  constructor(private modalService: NgbModal, private spinner: NgxSpinnerService, private heightService: HeightService) {}

  ngOnInit() {
    this.loadAll();
  }

  // loa du lieu bang
  loadAll() {
    //this.spinner.show();
    this.searchForm.humanResourceId = this.form.value.humanResourceId;
    this.searchForm.partId = this.form.value.partId;
    this.searchForm.active = this.form.value.active;
    this.searchForm.departmentId = this.form.value.departmentId;
    this.searchForm.positionId = this.form.value.positionId;
    this.searchForm.experience = this.form.value.experience;
    this.searchForm.lstMajorId = this.form.value.lstMajorId;
    //debugger;
    this.searchForm.page = this.page;
    this.searchForm.pageSize = this.itemsPerPage;
    // this.humanResourcesApiService
    //   .searchHumanResources(
    //     this.searchForm
    //   )
    //   .subscribe(
    //     res => {
    //       this.spinner.hide();
    //       this.paginateUserList(res);
    //     },
    //     err => {
    //       this.spinner.hide();
    //       // this.toastService.openErrorToast(this.translateService.instant('common.toastr.messages.error.load'));
    //       this.toastService.openErrorToast("loi");
    //     }
    //   );
  }

  /////////////
  onResize() {
    this.height = this.heightService.onResizeWithoutFooter();
  }

  openModalAddUser(type?: string, selectedData?: any) {
    const modalRef = this.modalService.open(AddRoomComponent, {
      size: 'lg',
      backdrop: 'static',
      keyboard: false
    });
    modalRef.componentInstance.type = type;
    modalRef.componentInstance.id = selectedData ? selectedData.humanResourceId : null;
    console.warn('tesst' + modalRef.componentInstance.id);

    modalRef.result
      .then(result => {
        if (result) {
          this.loadAll();
        }
      })
      .catch(() => {
        this.loadAll();
      });
  }
}
