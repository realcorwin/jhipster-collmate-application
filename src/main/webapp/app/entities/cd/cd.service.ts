import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICd } from 'app/shared/model/cd.model';

type EntityResponseType = HttpResponse<ICd>;
type EntityArrayResponseType = HttpResponse<ICd[]>;

@Injectable({ providedIn: 'root' })
export class CdService {
  public resourceUrl = SERVER_API_URL + 'api/cds';

  constructor(protected http: HttpClient) {}

  create(cd: ICd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cd);
    return this.http
      .post<ICd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cd: ICd): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cd);
    return this.http
      .put<ICd>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICd[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cd: ICd): ICd {
    const copy: ICd = Object.assign({}, cd, {
      added: cd.added != null && cd.added.isValid() ? cd.added.toJSON() : null
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
      res.body.forEach((cd: ICd) => {
        cd.added = cd.added != null ? moment(cd.added) : null;
      });
    }
    return res;
  }
}
