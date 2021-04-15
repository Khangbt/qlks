import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { HumanResourcesComponent } from "app/modules/system-categories/human-resources/human-resources.component";

const routes: Routes = [
 
  {
    path: 'human-resources',
    component: HumanResourcesComponent,
    canActivate: [],
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/human-resources'
    }
  },
  
  {
    path: 'human-management',
    loadChildren: () => import('./human-resources/human-resources.module').then(m => m.HumanResourcesModule),
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      defaultSort: 'id,asc',
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/human-resources'
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemCategoriesRoutingModule {
}
