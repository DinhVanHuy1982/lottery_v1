import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArticleGroup } from '../article-group.model';
import { ArticleGroupService } from '../service/article-group.service';

export const articleGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | IArticleGroup> => {
  const id = route.params['id'];
  if (id) {
    return inject(ArticleGroupService)
      .find(id)
      .pipe(
        mergeMap((articleGroup: HttpResponse<IArticleGroup>) => {
          if (articleGroup.body) {
            return of(articleGroup.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default articleGroupResolve;
