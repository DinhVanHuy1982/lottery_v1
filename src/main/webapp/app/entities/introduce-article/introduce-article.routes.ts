import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { IntroduceArticleComponent } from './list/introduce-article.component';
import { IntroduceArticleDetailComponent } from './detail/introduce-article-detail.component';
import { IntroduceArticleUpdateComponent } from './update/introduce-article-update.component';
import IntroduceArticleResolve from './route/introduce-article-routing-resolve.service';

const introduceArticleRoute: Routes = [
  {
    path: '',
    component: IntroduceArticleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IntroduceArticleDetailComponent,
    resolve: {
      introduceArticle: IntroduceArticleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IntroduceArticleUpdateComponent,
    resolve: {
      introduceArticle: IntroduceArticleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IntroduceArticleUpdateComponent,
    resolve: {
      introduceArticle: IntroduceArticleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default introduceArticleRoute;
