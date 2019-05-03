import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cd } from 'app/shared/model/cd.model';
import { CdService } from './cd.service';
import { CdComponent } from './cd.component';
import { CdDetailComponent } from './cd-detail.component';
import { CdUpdateComponent } from './cd-update.component';
import { CdDeletePopupComponent } from './cd-delete-dialog.component';
import { ICd } from 'app/shared/model/cd.model';

@Injectable({ providedIn: 'root' })
export class CdResolve implements Resolve<ICd> {
  constructor(private service: CdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICd> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cd>) => response.ok),
        map((cd: HttpResponse<Cd>) => cd.body)
      );
    }
    return of(new Cd());
  }
}

export const cdRoute: Routes = [
  {
    path: '',
    component: CdComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'collmateApp.cd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CdDetailComponent,
    resolve: {
      cd: CdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.cd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CdUpdateComponent,
    resolve: {
      cd: CdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.cd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CdUpdateComponent,
    resolve: {
      cd: CdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.cd.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CdDeletePopupComponent,
    resolve: {
      cd: CdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.cd.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
