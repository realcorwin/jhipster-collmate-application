/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CollmateTestModule } from '../../../test.module';
import { DvdUpdateComponent } from 'app/entities/dvd/dvd-update.component';
import { DvdService } from 'app/entities/dvd/dvd.service';
import { Dvd } from 'app/shared/model/dvd.model';

describe('Component Tests', () => {
  describe('Dvd Management Update Component', () => {
    let comp: DvdUpdateComponent;
    let fixture: ComponentFixture<DvdUpdateComponent>;
    let service: DvdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CollmateTestModule],
        declarations: [DvdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DvdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DvdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DvdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dvd('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dvd();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
