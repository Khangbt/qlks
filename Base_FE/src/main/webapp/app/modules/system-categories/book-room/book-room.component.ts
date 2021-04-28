import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddBookRoomComponent } from './add-book-room/add-book-room.component';
@Component({
  selector: 'jhi-book-room',
  templateUrl: './book-room.component.html',
  styleUrls: ['./book-room.component.scss']
})
export class BookRoomComponent implements OnInit {
  data = {};
  constructor(private modalService: NgbModal) {}

  ngOnInit() {}
  openModalAddBookRoom(type, data) {
    const modalRef = this.modalService.open(AddBookRoomComponent, {
      size: 'lg',
      backdrop: 'static',
      keyboard: false
    });
    modalRef.componentInstance.type = type;
    modalRef.componentInstance.id = data ? data.humanResourceId : null;
    console.warn('tesst' + modalRef.componentInstance.id);

    modalRef.result
      .then(result => {
        if (result) {
          this.loadAll();
        }
      })
      .catch(() => {});
  }
  loadAll() {}
}
