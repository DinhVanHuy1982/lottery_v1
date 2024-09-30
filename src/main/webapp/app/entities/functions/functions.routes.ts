import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FunctionsComponent } from './list/functions.component';
import { FunctionsDetailComponent } from './detail/functions-detail.component';
import { FunctionsUpdateComponent } from './update/functions-update.component';
import FunctionsResolve from './route/functions-routing-resolve.service';

const functionsRoute: Routes = [
  {
    path: '',
    component: FunctionsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FunctionsDetailComponent,
    resolve: {
      functions: FunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FunctionsUpdateComponent,
    resolve: {
      functions: FunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FunctionsUpdateComponent,
    resolve: {
      functions: FunctionsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default functionsRoute;
