import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFunctions } from '../functions.model';
import { FunctionsService } from '../service/functions.service';

export const functionsResolve = (route: ActivatedRouteSnapshot): Observable<null | IFunctions> => {
  const id = route.params['id'];
  if (id) {
    return inject(FunctionsService)
      .find(id)
      .pipe(
        mergeMap((functions: HttpResponse<IFunctions>) => {
          if (functions.body) {
            return of(functions.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default functionsResolve;
