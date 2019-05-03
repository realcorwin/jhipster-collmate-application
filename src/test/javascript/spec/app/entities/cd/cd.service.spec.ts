/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CdService } from 'app/entities/cd/cd.service';
import { ICd, Cd, State } from 'app/shared/model/cd.model';

describe('Service Tests', () => {
  describe('Cd Service', () => {
    let injector: TestBed;
    let service: CdService;
    let httpMock: HttpTestingController;
    let elemDefault: ICd;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CdService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Cd('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', State.OK, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            added: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Cd', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            added: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            added: currentDate
          },
          returnedFromService
        );
        service
          .create(new Cd(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Cd', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            performer: 'BBBBBB',
            releaseYear: 'BBBBBB',
            diskCount: 'BBBBBB',
            medium: 'BBBBBB',
            label: 'BBBBBB',
            state: 'BBBBBB',
            added: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            added: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Cd', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            performer: 'BBBBBB',
            releaseYear: 'BBBBBB',
            diskCount: 'BBBBBB',
            medium: 'BBBBBB',
            label: 'BBBBBB',
            state: 'BBBBBB',
            added: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            added: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Cd', async () => {
        const rxPromise = service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
