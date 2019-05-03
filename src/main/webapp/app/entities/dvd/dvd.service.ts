import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDvd } from 'app/shared/model/dvd.model';

type EntityResponseType = HttpResponse<IDvd>;
type EntityArrayResponseType = HttpResponse<IDvd[]>;

@Injectable({ providedIn: 'root' })
export class DvdService {
  public resourceUrl = SERVER_API_URL + 'api/dvds';

  constructor(protected http: HttpClient) {}

  create(dvd: IDvd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dvd);
    return this.http
      .post<IDvd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dvd: IDvd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dvd);
    return this.http
      .put<IDvd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IDvd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDvd[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dvd: IDvd): IDvd {
    const copy: IDvd = Object.assign({}, dvd, {
      added: dvd.added != null && dvd.added.isValid() ? dvd.added.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.added = res.body.added != null ? moment(res.body.added) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dvd: IDvd) => {
        dvd.added = dvd.added != null ? moment(dvd.added) : null;
      });
    }
    return res;
  }
}
