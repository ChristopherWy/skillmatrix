import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SkillmatrixSharedModule } from 'app/shared/shared.module';
import { ArbeitszeitenComponent } from './arbeitszeiten.component';
import { ArbeitszeitenDetailComponent } from './arbeitszeiten-detail.component';
import { ArbeitszeitenUpdateComponent } from './arbeitszeiten-update.component';
import { ArbeitszeitenDeleteDialogComponent } from './arbeitszeiten-delete-dialog.component';
import { arbeitszeitenRoute } from './arbeitszeiten.route';

@NgModule({
  imports: [SkillmatrixSharedModule, RouterModule.forChild(arbeitszeitenRoute)],
  declarations: [ArbeitszeitenComponent, ArbeitszeitenDetailComponent, ArbeitszeitenUpdateComponent, ArbeitszeitenDeleteDialogComponent],
  entryComponents: [ArbeitszeitenDeleteDialogComponent],
})
export class SkillmatrixArbeitszeitenModule {}
