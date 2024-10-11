import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { LevelDepositsComponent } from './list/level-deposits.component';
import { LevelDepositsDetailComponent } from './detail/level-deposits-detail.component';
import { LevelDepositsUpdateComponent } from './update/level-deposits-update.component';
import LevelDepositsResolve from './route/level-deposits-routing-resolve.service';

const levelDepositsRoute: Routes = [
  {
    path: '',
    component: LevelDepositsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LevelDepositsDetailComponent,
    resolve: {
      levelDeposits: LevelDepositsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LevelDepositsUpdateComponent,
    resolve: {
      levelDeposits: LevelDepositsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LevelDepositsUpdateComponent,
    resolve: {
      levelDeposits: LevelDepositsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default levelDepositsRoute;
