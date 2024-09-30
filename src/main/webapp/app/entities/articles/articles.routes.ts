import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ArticlesComponent } from './list/articles.component';
import { ArticlesDetailComponent } from './detail/articles-detail.component';
import { ArticlesUpdateComponent } from './update/articles-update.component';
import ArticlesResolve from './route/articles-routing-resolve.service';

const articlesRoute: Routes = [
  {
    path: '',
    component: ArticlesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArticlesDetailComponent,
    resolve: {
      articles: ArticlesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArticlesUpdateComponent,
    resolve: {
      articles: ArticlesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArticlesUpdateComponent,
    resolve: {
      articles: ArticlesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default articlesRoute;
