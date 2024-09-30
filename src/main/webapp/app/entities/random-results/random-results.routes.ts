import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RandomResultsComponent } from './list/random-results.component';
import { RandomResultsDetailComponent } from './detail/random-results-detail.component';
import { RandomResultsUpdateComponent } from './update/random-results-update.component';
import RandomResultsResolve from './route/random-results-routing-resolve.service';

const randomResultsRoute: Routes = [
  {
    path: '',
    component: RandomResultsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RandomResultsDetailComponent,
    resolve: {
      randomResults: RandomResultsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RandomResultsUpdateComponent,
    resolve: {
      randomResults: RandomResultsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RandomResultsUpdateComponent,
    resolve: {
      randomResults: RandomResultsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default randomResultsRoute;
