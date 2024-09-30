import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DepositsComponent } from './list/deposits.component';
import { DepositsDetailComponent } from './detail/deposits-detail.component';
import { DepositsUpdateComponent } from './update/deposits-update.component';
import DepositsResolve from './route/deposits-routing-resolve.service';

const depositsRoute: Routes = [
  {
    path: '',
    component: DepositsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepositsDetailComponent,
    resolve: {
      deposits: DepositsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepositsUpdateComponent,
    resolve: {
      deposits: DepositsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepositsUpdateComponent,
    resolve: {
      deposits: DepositsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default depositsRoute;
