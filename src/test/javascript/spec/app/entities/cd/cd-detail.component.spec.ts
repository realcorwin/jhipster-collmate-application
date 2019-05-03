/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CollmateTestModule } from '../../../test.module';
import { CdDetailComponent } from 'app/entities/cd/cd-detail.component';
import { Cd } from 'app/shared/model/cd.model';

describe('Component Tests', () => {
  describe('Cd Management Detail Component', () => {
    let comp: CdDetailComponent;
    let fixture: ComponentFixture<CdDetailComponent>;
    const route = ({ data: of({ cd: new Cd('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CollmateTestModule],
        declarations: [CdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cd).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
