import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SkillmatrixSharedModule } from 'app/shared/shared.module';
import { MitarbeiterComponent } from './mitarbeiter.component';
import { MitarbeiterDetailComponent } from './mitarbeiter-detail.component';
import { MitarbeiterUpdateComponent } from './mitarbeiter-update.component';
import { MitarbeiterDeleteDialogComponent } from './mitarbeiter-delete-dialog.component';
import { mitarbeiterRoute } from './mitarbeiter.route';

@NgModule({
  imports: [SkillmatrixSharedModule, RouterModule.forChild(mitarbeiterRoute)],
  declarations: [MitarbeiterComponent, MitarbeiterDetailComponent, MitarbeiterUpdateComponent, MitarbeiterDeleteDialogComponent],
  entryComponents: [MitarbeiterDeleteDialogComponent],
})
export class SkillmatrixMitarbeiterModule {}
