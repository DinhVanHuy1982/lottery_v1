import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { LevelDepositsResultComponent } from './list/level-deposits-result.component';
import { LevelDepositsResultDetailComponent } from './detail/level-deposits-result-detail.component';
import { LevelDepositsResultUpdateComponent } from './update/level-deposits-result-update.component';
import LevelDepositsResultResolve from './route/level-deposits-result-routing-resolve.service';

const levelDepositsResultRoute: Routes = [
  {
    path: '',
    component: LevelDepositsResultComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LevelDepositsResultDetailComponent,
    resolve: {
      levelDepositsResult: LevelDepositsResultResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LevelDepositsResultUpdateComponent,
    resolve: {
      levelDepositsResult: LevelDepositsResultResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LevelDepositsResultUpdateComponent,
    resolve: {
      levelDepositsResult: LevelDepositsResultResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default levelDepositsResultRoute;
