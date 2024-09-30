import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIntroduceArticle } from '../introduce-article.model';
import { IntroduceArticleService } from '../service/introduce-article.service';

export const introduceArticleResolve = (route: ActivatedRouteSnapshot): Observable<null | IIntroduceArticle> => {
  const id = route.params['id'];
  if (id) {
    return inject(IntroduceArticleService)
      .find(id)
      .pipe(
        mergeMap((introduceArticle: HttpResponse<IIntroduceArticle>) => {
          if (introduceArticle.body) {
            return of(introduceArticle.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default introduceArticleResolve;
