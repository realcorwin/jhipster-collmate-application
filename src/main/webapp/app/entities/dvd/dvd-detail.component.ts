import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDvd } from 'app/shared/model/dvd.model';

@Component({
  selector: 'jhi-dvd-detail',
  templateUrl: './dvd-detail.component.html'
})
export class DvdDetailComponent implements OnInit {
  dvd: IDvd;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dvd }) => {
      this.dvd = dvd;
    });
  }

  previousState() {
    window.history.back();
  }
}
