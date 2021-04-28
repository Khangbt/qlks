import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreeViewModule } from '@syncfusion/ej2-angular-navigations';
import { ChartsModule } from 'ng2-charts';
import { InvoiceWebappSharedModule } from 'app/shared/shared.module';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { Test1Component } from 'app/modules/system-categories/test1/test1.component';
import { Test1RoutingModule } from 'app/modules/system-categories/test1/test1-routing.module';

@NgModule({
  declarations: [Test1Component],
  imports: [CommonModule, PerfectScrollbarModule, InvoiceWebappSharedModule, ChartsModule, TreeViewModule, Test1RoutingModule],
  entryComponents: []
})
export class Test1Module {}
