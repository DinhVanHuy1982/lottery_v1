import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILevelDeposits } from '../level-deposits.model';
import { LevelDepositsService } from '../service/level-deposits.service';

export const levelDepositsResolve = (route: ActivatedRouteSnapshot): Observable<null | ILevelDeposits> => {
  const id = route.params['id'];
  if (id) {
    return inject(LevelDepositsService)
      .find(id)
      .pipe(
        mergeMap((levelDeposits: HttpResponse<ILevelDeposits>) => {
          if (levelDeposits.body) {
            return of(levelDeposits.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default levelDepositsResolve;
