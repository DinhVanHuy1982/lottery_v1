import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ArticleGroupComponent } from './list/article-group.component';
import { ArticleGroupDetailComponent } from './detail/article-group-detail.component';
import { ArticleGroupUpdateComponent } from './update/article-group-update.component';
import ArticleGroupResolve from './route/article-group-routing-resolve.service';

const articleGroupRoute: Routes = [
  {
    path: '',
    component: ArticleGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArticleGroupDetailComponent,
    resolve: {
      articleGroup: ArticleGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArticleGroupUpdateComponent,
    resolve: {
      articleGroup: ArticleGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArticleGroupUpdateComponent,
    resolve: {
      articleGroup: ArticleGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default articleGroupRoute;
