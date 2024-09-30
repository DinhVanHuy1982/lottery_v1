import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArticles } from '../articles.model';
import { ArticlesService } from '../service/articles.service';

export const articlesResolve = (route: ActivatedRouteSnapshot): Observable<null | IArticles> => {
  const id = route.params['id'];
  if (id) {
    return inject(ArticlesService)
      .find(id)
      .pipe(
        mergeMap((articles: HttpResponse<IArticles>) => {
          if (articles.body) {
            return of(articles.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default articlesResolve;
