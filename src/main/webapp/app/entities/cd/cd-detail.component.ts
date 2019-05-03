import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICd } from 'app/shared/model/cd.model';

@Component({
  selector: 'jhi-cd-detail',
  templateUrl: './cd-detail.component.html'
})
export class CdDetailComponent implements OnInit {
  cd: ICd;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cd }) => {
      this.cd = cd;
    });
  }

  previousState() {
    window.history.back();
  }
}
