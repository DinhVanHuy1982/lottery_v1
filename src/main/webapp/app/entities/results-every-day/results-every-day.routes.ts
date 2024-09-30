import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ResultsEveryDayComponent } from './list/results-every-day.component';
import { ResultsEveryDayDetailComponent } from './detail/results-every-day-detail.component';
import { ResultsEveryDayUpdateComponent } from './update/results-every-day-update.component';
import ResultsEveryDayResolve from './route/results-every-day-routing-resolve.service';

const resultsEveryDayRoute: Routes = [
  {
    path: '',
    component: ResultsEveryDayComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultsEveryDayDetailComponent,
    resolve: {
      resultsEveryDay: ResultsEveryDayResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultsEveryDayUpdateComponent,
    resolve: {
      resultsEveryDay: ResultsEveryDayResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultsEveryDayUpdateComponent,
    resolve: {
      resultsEveryDay: ResultsEveryDayResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default resultsEveryDayRoute;
