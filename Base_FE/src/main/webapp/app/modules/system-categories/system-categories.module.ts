import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SystemCategoriesRoutingModule} from 'app/modules/system-categories/system-categories-routing.module';
import {InvoiceWebappSharedModule} from 'app/shared/shared.module';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {ChartsModule} from 'ng2-charts';
import {ConvertStatusPipe} from "app/shared/pipes/convert-status.pipe";
import {MaxLengthTextPipe} from "app/shared/pipes/max-length-text.pipe";
import {ImportExcelHumanResourceComponent} from "app/modules/system-categories/human-resources/import-excel-human-resource/import-excel-human-resource.component";
import {HumanResourcesModule} from "app/modules/system-categories/human-resources/human-resources.module";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

import {NgxQRCodeModule} from "ngx-qrcode2";
@NgModule({
  declarations: [
   
    ImportExcelHumanResourceComponent,
    // CustomerComponent,
   
  ],
  imports: [
    CommonModule,
    SystemCategoriesRoutingModule,
    PerfectScrollbarModule,
    InvoiceWebappSharedModule,
    ChartsModule,
    HumanResourcesModule,
    NgxQRCodeModule,  
  ],
  entryComponents: [   
    ImportExcelHumanResourceComponent,
  ],
  exports: [
  ],
  providers: [ConvertStatusPipe, MaxLengthTextPipe, NgbActiveModal]
})
export class SystemCategoriesModule {
}
