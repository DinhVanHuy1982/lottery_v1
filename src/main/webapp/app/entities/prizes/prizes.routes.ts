import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PrizesComponent } from './list/prizes.component';
import { PrizesDetailComponent } from './detail/prizes-detail.component';
import { PrizesUpdateComponent } from './update/prizes-update.component';
import PrizesResolve from './route/prizes-routing-resolve.service';

const prizesRoute: Routes = [
  {
    path: '',
    component: PrizesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrizesDetailComponent,
    resolve: {
      prizes: PrizesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrizesUpdateComponent,
    resolve: {
      prizes: PrizesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrizesUpdateComponent,
    resolve: {
      prizes: PrizesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default prizesRoute;
