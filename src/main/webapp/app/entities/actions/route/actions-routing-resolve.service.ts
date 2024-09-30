import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActions } from '../actions.model';
import { ActionsService } from '../service/actions.service';

export const actionsResolve = (route: ActivatedRouteSnapshot): Observable<null | IActions> => {
  const id = route.params['id'];
  if (id) {
    return inject(ActionsService)
      .find(id)
      .pipe(
        mergeMap((actions: HttpResponse<IActions>) => {
          if (actions.body) {
            return of(actions.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default actionsResolve;
