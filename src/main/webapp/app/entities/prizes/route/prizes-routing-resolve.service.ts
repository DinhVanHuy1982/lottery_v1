import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrizes } from '../prizes.model';
import { PrizesService } from '../service/prizes.service';

export const prizesResolve = (route: ActivatedRouteSnapshot): Observable<null | IPrizes> => {
  const id = route.params['id'];
  if (id) {
    return inject(PrizesService)
      .find(id)
      .pipe(
        mergeMap((prizes: HttpResponse<IPrizes>) => {
          if (prizes.body) {
            return of(prizes.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default prizesResolve;
