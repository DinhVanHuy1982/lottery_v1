import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIntroduceArticleGroup } from '../introduce-article-group.model';
import { IntroduceArticleGroupService } from '../service/introduce-article-group.service';

export const introduceArticleGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | IIntroduceArticleGroup> => {
  const id = route.params['id'];
  if (id) {
    return inject(IntroduceArticleGroupService)
      .find(id)
      .pipe(
        mergeMap((introduceArticleGroup: HttpResponse<IIntroduceArticleGroup>) => {
          if (introduceArticleGroup.body) {
            return of(introduceArticleGroup.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default introduceArticleGroupResolve;
