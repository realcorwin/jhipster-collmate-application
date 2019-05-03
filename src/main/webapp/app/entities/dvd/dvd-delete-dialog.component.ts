import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDvd } from 'app/shared/model/dvd.model';
import { DvdService } from './dvd.service';

@Component({
  selector: 'jhi-dvd-delete-dialog',
  templateUrl: './dvd-delete-dialog.component.html'
})
export class DvdDeleteDialogComponent {
  dvd: IDvd;

  constructor(protected dvdService: DvdService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.dvdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dvdListModification',
        content: 'Deleted an dvd'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dvd-delete-popup',
  template: ''
})
export class DvdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dvd }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DvdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dvd = dvd;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dvd', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dvd', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
