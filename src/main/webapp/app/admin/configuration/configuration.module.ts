import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SkillmatrixSharedModule } from 'app/shared/shared.module';

import { ConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [SkillmatrixSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [ConfigurationComponent],
})
export class ConfigurationModule {}
