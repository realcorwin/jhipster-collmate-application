import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICd, Cd } from 'app/shared/model/cd.model';
import { CdService } from './cd.service';

@Component({
  selector: 'jhi-cd-update',
  templateUrl: './cd-update.component.html'
})
export class CdUpdateComponent implements OnInit {
  cd: ICd;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    performer: [],
    releaseYear: [],
    diskCount: [],
    medium: [],
    label: [],
    state: [],
    added: []
  });

  constructor(protected cdService: CdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cd }) => {
      this.updateForm(cd);
      this.cd = cd;
    });
  }

  updateForm(cd: ICd) {
    this.editForm.patchValue({
      id: cd.id,
      name: cd.name,
      performer: cd.performer,
      releaseYear: cd.releaseYear,
      diskCount: cd.diskCount,
      medium: cd.medium,
      label: cd.label,
      state: cd.state,
      added: cd.added != null ? cd.added.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cd = this.createFromForm();
    if (cd.id !== undefined) {
      this.subscribeToSaveResponse(this.cdService.update(cd));
    } else {
      this.subscribeToSaveResponse(this.cdService.create(cd));
    }
  }

  private createFromForm(): ICd {
    const entity = {
      ...new Cd(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      performer: this.editForm.get(['performer']).value,
      releaseYear: this.editForm.get(['releaseYear']).value,
      diskCount: this.editForm.get(['diskCount']).value,
      medium: this.editForm.get(['medium']).value,
      label: this.editForm.get(['label']).value,
      state: this.editForm.get(['state']).value,
      added: this.editForm.get(['added']).value != null ? moment(this.editForm.get(['added']).value, DATE_TIME_FORMAT) : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICd>>) {
    result.subscribe((res: HttpResponse<ICd>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
