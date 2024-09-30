import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResultsEveryDay } from '../results-every-day.model';
import { ResultsEveryDayService } from '../service/results-every-day.service';

export const resultsEveryDayResolve = (route: ActivatedRouteSnapshot): Observable<null | IResultsEveryDay> => {
  const id = route.params['id'];
  if (id) {
    return inject(ResultsEveryDayService)
      .find(id)
      .pipe(
        mergeMap((resultsEveryDay: HttpResponse<IResultsEveryDay>) => {
          if (resultsEveryDay.body) {
            return of(resultsEveryDay.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default resultsEveryDayResolve;
