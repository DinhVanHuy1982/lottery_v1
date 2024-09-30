import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRandomResults } from '../random-results.model';
import { RandomResultsService } from '../service/random-results.service';

export const randomResultsResolve = (route: ActivatedRouteSnapshot): Observable<null | IRandomResults> => {
  const id = route.params['id'];
  if (id) {
    return inject(RandomResultsService)
      .find(id)
      .pipe(
        mergeMap((randomResults: HttpResponse<IRandomResults>) => {
          if (randomResults.body) {
            return of(randomResults.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default randomResultsResolve;
