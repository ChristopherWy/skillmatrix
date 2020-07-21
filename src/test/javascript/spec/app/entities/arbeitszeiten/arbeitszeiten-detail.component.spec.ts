import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SkillmatrixTestModule } from '../../../test.module';
import { ArbeitszeitenDetailComponent } from 'app/entities/arbeitszeiten/arbeitszeiten-detail.component';
import { Arbeitszeiten } from 'app/shared/model/arbeitszeiten.model';

describe('Component Tests', () => {
  describe('Arbeitszeiten Management Detail Component', () => {
    let comp: ArbeitszeitenDetailComponent;
    let fixture: ComponentFixture<ArbeitszeitenDetailComponent>;
    const route = ({ data: of({ arbeitszeiten: new Arbeitszeiten(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SkillmatrixTestModule],
        declarations: [ArbeitszeitenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArbeitszeitenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArbeitszeitenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load arbeitszeiten on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.arbeitszeiten).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
