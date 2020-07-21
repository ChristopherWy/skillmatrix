import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SkillmatrixSharedModule } from 'app/shared/shared.module';
import { SkillmatrixCoreModule } from 'app/core/core.module';
import { SkillmatrixAppRoutingModule } from './app-routing.module';
import { SkillmatrixHomeModule } from './home/home.module';
import { SkillmatrixEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SkillmatrixSharedModule,
    SkillmatrixCoreModule,
    SkillmatrixHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SkillmatrixEntityModule,
    SkillmatrixAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SkillmatrixAppModule {}
