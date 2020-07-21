import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SkillmatrixSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [SkillmatrixSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class SkillmatrixHomeModule {}
