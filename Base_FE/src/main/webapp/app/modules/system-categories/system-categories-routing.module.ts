import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { HumanResourcesComponent } from 'app/modules/system-categories/human-resources/human-resources.component';
import { Test1Component } from 'app/modules/system-categories/test1/test1.component';

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

  {
    path: 'test1',
    component: Test1Component,
    canActivate: [],
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/test1'
    }
  },

  {
    path: 'test1',
    loadChildren: () => import('./test1/test1.module').then(m => m.Test1Module),
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      defaultSort: 'id,asc',
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/test1'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemCategoriesRoutingModule {}
