import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILevelDepositsResult } from '../level-deposits-result.model';
import { LevelDepositsResultService } from '../service/level-deposits-result.service';

export const levelDepositsResultResolve = (route: ActivatedRouteSnapshot): Observable<null | ILevelDepositsResult> => {
  const id = route.params['id'];
  if (id) {
    return inject(LevelDepositsResultService)
      .find(id)
      .pipe(
        mergeMap((levelDepositsResult: HttpResponse<ILevelDepositsResult>) => {
          if (levelDepositsResult.body) {
            return of(levelDepositsResult.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default levelDepositsResultResolve;
