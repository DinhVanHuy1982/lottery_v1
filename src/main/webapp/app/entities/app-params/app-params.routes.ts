import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AppParamsComponent } from './list/app-params.component';
import { AppParamsDetailComponent } from './detail/app-params-detail.component';
import { AppParamsUpdateComponent } from './update/app-params-update.component';
import AppParamsResolve from './route/app-params-routing-resolve.service';

const appParamsRoute: Routes = [
  {
    path: '',
    component: AppParamsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AppParamsDetailComponent,
    resolve: {
      appParams: AppParamsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AppParamsUpdateComponent,
    resolve: {
      appParams: AppParamsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AppParamsUpdateComponent,
    resolve: {
      appParams: AppParamsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default appParamsRoute;
