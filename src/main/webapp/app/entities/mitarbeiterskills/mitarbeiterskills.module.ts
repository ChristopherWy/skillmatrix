import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SkillmatrixSharedModule } from 'app/shared/shared.module';
import { MitarbeiterskillsComponent } from './mitarbeiterskills.component';
import { MitarbeiterskillsDetailComponent } from './mitarbeiterskills-detail.component';
import { MitarbeiterskillsUpdateComponent } from './mitarbeiterskills-update.component';
import { MitarbeiterskillsDeleteDialogComponent } from './mitarbeiterskills-delete-dialog.component';
import { mitarbeiterskillsRoute } from './mitarbeiterskills.route';

@NgModule({
  imports: [SkillmatrixSharedModule, RouterModule.forChild(mitarbeiterskillsRoute)],
  declarations: [
    MitarbeiterskillsComponent,
    MitarbeiterskillsDetailComponent,
    MitarbeiterskillsUpdateComponent,
    MitarbeiterskillsDeleteDialogComponent,
  ],
  entryComponents: [MitarbeiterskillsDeleteDialogComponent],
})
export class SkillmatrixMitarbeiterskillsModule {}
