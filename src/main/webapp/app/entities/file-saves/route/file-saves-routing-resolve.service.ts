import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFileSaves } from '../file-saves.model';
import { FileSavesService } from '../service/file-saves.service';

export const fileSavesResolve = (route: ActivatedRouteSnapshot): Observable<null | IFileSaves> => {
  const id = route.params['id'];
  if (id) {
    return inject(FileSavesService)
      .find(id)
      .pipe(
        mergeMap((fileSaves: HttpResponse<IFileSaves>) => {
          if (fileSaves.body) {
            return of(fileSaves.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default fileSavesResolve;
