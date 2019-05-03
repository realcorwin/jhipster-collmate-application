import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CollmateSharedModule } from 'app/shared';
import {
  CdComponent,
  CdDetailComponent,
  CdUpdateComponent,
  CdDeletePopupComponent,
  CdDeleteDialogComponent,
  cdRoute,
  cdPopupRoute
} from './';

const ENTITY_STATES = [...cdRoute, ...cdPopupRoute];

@NgModule({
  imports: [CollmateSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CdComponent, CdDetailComponent, CdUpdateComponent, CdDeleteDialogComponent, CdDeletePopupComponent],
  entryComponents: [CdComponent, CdUpdateComponent, CdDeleteDialogComponent, CdDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CollmateCdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
