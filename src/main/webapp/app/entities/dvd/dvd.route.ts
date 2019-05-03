import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dvd } from 'app/shared/model/dvd.model';
import { DvdService } from './dvd.service';
import { DvdComponent } from './dvd.component';
import { DvdDetailComponent } from './dvd-detail.component';
import { DvdUpdateComponent } from './dvd-update.component';
import { DvdDeletePopupComponent } from './dvd-delete-dialog.component';
import { IDvd } from 'app/shared/model/dvd.model';

@Injectable({ providedIn: 'root' })
export class DvdResolve implements Resolve<IDvd> {
  constructor(private service: DvdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDvd> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Dvd>) => response.ok),
        map((dvd: HttpResponse<Dvd>) => dvd.body)
      );
    }
    return of(new Dvd());
  }
}

export const dvdRoute: Routes = [
  {
    path: '',
    component: DvdComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'collmateApp.dvd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DvdDetailComponent,
    resolve: {
      dvd: DvdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.dvd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DvdUpdateComponent,
    resolve: {
      dvd: DvdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.dvd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DvdUpdateComponent,
    resolve: {
      dvd: DvdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.dvd.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dvdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DvdDeletePopupComponent,
    resolve: {
      dvd: DvdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'collmateApp.dvd.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
