import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDeposits } from '../deposits.model';
import { DepositsService } from '../service/deposits.service';

export const depositsResolve = (route: ActivatedRouteSnapshot): Observable<null | IDeposits> => {
  const id = route.params['id'];
  if (id) {
    return inject(DepositsService)
      .find(id)
      .pipe(
        mergeMap((deposits: HttpResponse<IDeposits>) => {
          if (deposits.body) {
            return of(deposits.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default depositsResolve;
