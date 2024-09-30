import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { IntroduceArticleGroupComponent } from './list/introduce-article-group.component';
import { IntroduceArticleGroupDetailComponent } from './detail/introduce-article-group-detail.component';
import { IntroduceArticleGroupUpdateComponent } from './update/introduce-article-group-update.component';
import IntroduceArticleGroupResolve from './route/introduce-article-group-routing-resolve.service';

const introduceArticleGroupRoute: Routes = [
  {
    path: '',
    component: IntroduceArticleGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IntroduceArticleGroupDetailComponent,
    resolve: {
      introduceArticleGroup: IntroduceArticleGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IntroduceArticleGroupUpdateComponent,
    resolve: {
      introduceArticleGroup: IntroduceArticleGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IntroduceArticleGroupUpdateComponent,
    resolve: {
      introduceArticleGroup: IntroduceArticleGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default introduceArticleGroupRoute;
