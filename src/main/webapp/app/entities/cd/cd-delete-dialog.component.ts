import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICd } from 'app/shared/model/cd.model';
import { CdService } from './cd.service';

@Component({
  selector: 'jhi-cd-delete-dialog',
  templateUrl: './cd-delete-dialog.component.html'
})
export class CdDeleteDialogComponent {
  cd: ICd;

  constructor(protected cdService: CdService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.cdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cdListModification',
        content: 'Deleted an cd'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cd-delete-popup',
  template: ''
})
export class CdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cd }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cd = cd;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cd', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cd', { outlets: { popup: null } }]);
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
