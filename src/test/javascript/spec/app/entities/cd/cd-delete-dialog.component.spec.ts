/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CollmateTestModule } from '../../../test.module';
import { CdDeleteDialogComponent } from 'app/entities/cd/cd-delete-dialog.component';
import { CdService } from 'app/entities/cd/cd.service';

describe('Component Tests', () => {
  describe('Cd Management Delete Component', () => {
    let comp: CdDeleteDialogComponent;
    let fixture: ComponentFixture<CdDeleteDialogComponent>;
    let service: CdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CollmateTestModule],
        declarations: [CdDeleteDialogComponent]
      })
        .overrideTemplate(CdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CdService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
