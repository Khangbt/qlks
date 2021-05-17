import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreeViewModule } from '@syncfusion/ej2-angular-navigations';
import { ChartsModule } from 'ng2-charts';
import { InvoiceWebappSharedModule } from 'app/shared/shared.module';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { ChartResourcesRoutingModule } from 'app/modules/system-categories/chart/chart-resources-routing.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, PerfectScrollbarModule, InvoiceWebappSharedModule, ChartsModule, TreeViewModule, ChartResourcesRoutingModule],
  entryComponents: []
})
export class ChartResourcesModule {}
