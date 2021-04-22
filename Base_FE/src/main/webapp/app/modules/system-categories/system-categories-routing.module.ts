import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { HumanResourcesComponent } from 'app/modules/system-categories/human-resources/human-resources.component';
import { RoomComponent } from 'app/modules/system-categories/room/room.component';
import { RoomTypeComponent } from 'app/modules/system-categories/room-type/room-type.component';
import { ServiceComponent } from 'app/modules/system-categories/service/service.component';
import { AssetResuorceComponent } from 'app/modules/system-categories/asset-resuorce/asset-resuorce.component';

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
    path: 'asset-resource',
    component: AssetResuorceComponent,
    canActivate: [],
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/asset-resource'
    }
  },

  {
    path: 'room-resources',
    component: RoomComponent,
    canActivate: [],
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/room-resources'
    }
  },
  {
    path: 'room-type-resources',
    component: RoomTypeComponent,
    canActivate: [],
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/room-type-resources'
    }
  },
  {
    path: 'service-resources',
    component: ServiceComponent,
    canActivate: [],
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'organizationCategories.title',
      url: 'system-categories/service-resources'
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
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemCategoriesRoutingModule {}
