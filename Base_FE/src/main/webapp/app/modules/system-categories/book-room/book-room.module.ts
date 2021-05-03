import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreeViewModule } from '@syncfusion/ej2-angular-navigations';
import { ChartsModule } from 'ng2-charts';
import { InvoiceWebappSharedModule } from 'app/shared/shared.module';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { AssetResourcesRoutingModule } from 'app/modules/system-categories/asset-resuorce/asset-resources-routing.module';
import { BookRoomComponent } from './book-room.component';
import { AddBookRoomComponent } from './add-book-room/add-book-room.component';
import { AddBookingComponent } from './add-booking/add-booking.component';
// import { AddAssetComponent } from './add-asset/add-asset.component';

@NgModule({
  declarations: [AddBookRoomComponent, BookRoomComponent, AddBookingComponent, AddBookRoomComponent, AddBookingComponent],
  imports: [CommonModule, PerfectScrollbarModule, InvoiceWebappSharedModule, ChartsModule, TreeViewModule, AssetResourcesRoutingModule],
  entryComponents: [AddBookRoomComponent, AddBookingComponent]
})
export class BookRoomModule {}
