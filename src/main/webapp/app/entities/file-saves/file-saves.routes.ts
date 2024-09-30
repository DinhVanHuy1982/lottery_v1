import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FileSavesComponent } from './list/file-saves.component';
import { FileSavesDetailComponent } from './detail/file-saves-detail.component';
import { FileSavesUpdateComponent } from './update/file-saves-update.component';
import FileSavesResolve from './route/file-saves-routing-resolve.service';

const fileSavesRoute: Routes = [
  {
    path: '',
    component: FileSavesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FileSavesDetailComponent,
    resolve: {
      fileSaves: FileSavesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FileSavesUpdateComponent,
    resolve: {
      fileSaves: FileSavesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FileSavesUpdateComponent,
    resolve: {
      fileSaves: FileSavesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fileSavesRoute;
