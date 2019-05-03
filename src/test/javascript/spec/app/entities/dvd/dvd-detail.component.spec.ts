/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CollmateTestModule } from '../../../test.module';
import { DvdDetailComponent } from 'app/entities/dvd/dvd-detail.component';
import { Dvd } from 'app/shared/model/dvd.model';

describe('Component Tests', () => {
  describe('Dvd Management Detail Component', () => {
    let comp: DvdDetailComponent;
    let fixture: ComponentFixture<DvdDetailComponent>;
    const route = ({ data: of({ dvd: new Dvd('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CollmateTestModule],
        declarations: [DvdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DvdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DvdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dvd).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
