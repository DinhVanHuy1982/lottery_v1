import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRoles } from '../roles.model';
import { RolesService } from '../service/roles.service';

export const rolesResolve = (route: ActivatedRouteSnapshot): Observable<null | IRoles> => {
  const id = route.params['id'];
  if (id) {
    return inject(RolesService)
      .find(id)
      .pipe(
        mergeMap((roles: HttpResponse<IRoles>) => {
          if (roles.body) {
            return of(roles.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default rolesResolve;
