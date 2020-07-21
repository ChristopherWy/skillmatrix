import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'mitarbeiter',
        loadChildren: () => import('./mitarbeiter/mitarbeiter.module').then(m => m.SkillmatrixMitarbeiterModule),
      },
      {
        path: 'skill',
        loadChildren: () => import('./skill/skill.module').then(m => m.SkillmatrixSkillModule),
      },
      {
        path: 'mitarbeiterskills',
        loadChildren: () => import('./mitarbeiterskills/mitarbeiterskills.module').then(m => m.SkillmatrixMitarbeiterskillsModule),
      },
      {
        path: 'arbeitszeiten',
        loadChildren: () => import('./arbeitszeiten/arbeitszeiten.module').then(m => m.SkillmatrixArbeitszeitenModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SkillmatrixEntityModule {}
