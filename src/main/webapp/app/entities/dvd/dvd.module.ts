import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CollmateSharedModule } from 'app/shared';
import {
  DvdComponent,
  DvdDetailComponent,
  DvdUpdateComponent,
  DvdDeletePopupComponent,
  DvdDeleteDialogComponent,
  dvdRoute,
  dvdPopupRoute
} from './';

const ENTITY_STATES = [...dvdRoute, ...dvdPopupRoute];

@NgModule({
  imports: [CollmateSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DvdComponent, DvdDetailComponent, DvdUpdateComponent, DvdDeleteDialogComponent, DvdDeletePopupComponent],
  entryComponents: [DvdComponent, DvdUpdateComponent, DvdDeleteDialogComponent, DvdDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CollmateDvdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
