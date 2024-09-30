import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRoleFunctionAction } from '../role-function-action.model';
import { RoleFunctionActionService } from '../service/role-function-action.service';

export const roleFunctionActionResolve = (route: ActivatedRouteSnapshot): Observable<null | IRoleFunctionAction> => {
  const id = route.params['id'];
  if (id) {
    return inject(RoleFunctionActionService)
      .find(id)
      .pipe(
        mergeMap((roleFunctionAction: HttpResponse<IRoleFunctionAction>) => {
          if (roleFunctionAction.body) {
            return of(roleFunctionAction.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default roleFunctionActionResolve;
