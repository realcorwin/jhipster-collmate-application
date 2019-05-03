/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CollmateTestModule } from '../../../test.module';
import { CdUpdateComponent } from 'app/entities/cd/cd-update.component';
import { CdService } from 'app/entities/cd/cd.service';
import { Cd } from 'app/shared/model/cd.model';

describe('Component Tests', () => {
  describe('Cd Management Update Component', () => {
    let comp: CdUpdateComponent;
    let fixture: ComponentFixture<CdUpdateComponent>;
    let service: CdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CollmateTestModule],
        declarations: [CdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cd('123');
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
        const entity = new Cd();
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
