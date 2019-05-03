/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CollmateTestModule } from '../../../test.module';
import { DvdDeleteDialogComponent } from 'app/entities/dvd/dvd-delete-dialog.component';
import { DvdService } from 'app/entities/dvd/dvd.service';

describe('Component Tests', () => {
  describe('Dvd Management Delete Component', () => {
    let comp: DvdDeleteDialogComponent;
    let fixture: ComponentFixture<DvdDeleteDialogComponent>;
    let service: DvdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CollmateTestModule],
        declarations: [DvdDeleteDialogComponent]
      })
        .overrideTemplate(DvdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DvdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DvdService);
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
