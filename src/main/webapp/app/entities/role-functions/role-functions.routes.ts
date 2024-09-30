import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RoleFunctionsComponent } from './list/role-functions.component';
import { RoleFunctionsDetailComponent } from './detail/role-functions-detail.component';
import { RoleFunctionsUpdateComponent } from './update/role-functions-update.component';
import RoleFunctionsResolve from './route/role-functions-routing-resolve.service';

const roleFunctionsRoute: Routes = [
  {
    path: '',
    component: RoleFunctionsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RoleFunctionsDetailComponent,
    resolve: {
      roleFunctions: RoleFunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RoleFunctionsUpdateComponent,
    resolve: {
      roleFunctions: RoleFunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RoleFunctionsUpdateComponent,
    resolve: {
      roleFunctions: RoleFunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default roleFunctionsRoute;
