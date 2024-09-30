import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAppParams } from '../app-params.model';
import { AppParamsService } from '../service/app-params.service';

export const appParamsResolve = (route: ActivatedRouteSnapshot): Observable<null | IAppParams> => {
  const id = route.params['id'];
  if (id) {
    return inject(AppParamsService)
      .find(id)
      .pipe(
        mergeMap((appParams: HttpResponse<IAppParams>) => {
          if (appParams.body) {
            return of(appParams.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default appParamsResolve;
