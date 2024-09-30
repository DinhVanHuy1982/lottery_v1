import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RoleFunctionActionComponent } from './list/role-function-action.component';
import { RoleFunctionActionDetailComponent } from './detail/role-function-action-detail.component';
import { RoleFunctionActionUpdateComponent } from './update/role-function-action-update.component';
import RoleFunctionActionResolve from './route/role-function-action-routing-resolve.service';

const roleFunctionActionRoute: Routes = [
  {
    path: '',
    component: RoleFunctionActionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RoleFunctionActionDetailComponent,
    resolve: {
      roleFunctionAction: RoleFunctionActionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RoleFunctionActionUpdateComponent,
    resolve: {
      roleFunctionAction: RoleFunctionActionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RoleFunctionActionUpdateComponent,
    resolve: {
      roleFunctionAction: RoleFunctionActionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default roleFunctionActionRoute;
