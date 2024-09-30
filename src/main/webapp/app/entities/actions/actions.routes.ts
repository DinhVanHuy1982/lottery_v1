import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ActionsComponent } from './list/actions.component';
import { ActionsDetailComponent } from './detail/actions-detail.component';
import { ActionsUpdateComponent } from './update/actions-update.component';
import ActionsResolve from './route/actions-routing-resolve.service';

const actionsRoute: Routes = [
  {
    path: '',
    component: ActionsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActionsDetailComponent,
    resolve: {
      actions: ActionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActionsUpdateComponent,
    resolve: {
      actions: ActionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActionsUpdateComponent,
    resolve: {
      actions: ActionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default actionsRoute;
