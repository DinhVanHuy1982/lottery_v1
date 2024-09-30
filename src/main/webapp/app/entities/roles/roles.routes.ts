import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RolesComponent } from './list/roles.component';
import { RolesDetailComponent } from './detail/roles-detail.component';
import { RolesUpdateComponent } from './update/roles-update.component';
import RolesResolve from './route/roles-routing-resolve.service';

const rolesRoute: Routes = [
  {
    path: '',
    component: RolesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RolesDetailComponent,
    resolve: {
      roles: RolesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RolesUpdateComponent,
    resolve: {
      roles: RolesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RolesUpdateComponent,
    resolve: {
      roles: RolesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default rolesRoute;
