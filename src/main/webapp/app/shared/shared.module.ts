import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CollmateSharedLibsModule, CollmateSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [CollmateSharedLibsModule, CollmateSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [CollmateSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CollmateSharedModule {
  static forRoot() {
    return {
      ngModule: CollmateSharedModule
    };
  }
}
