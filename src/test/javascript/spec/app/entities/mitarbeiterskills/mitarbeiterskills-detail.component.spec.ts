import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SkillmatrixTestModule } from '../../../test.module';
import { MitarbeiterskillsDetailComponent } from 'app/entities/mitarbeiterskills/mitarbeiterskills-detail.component';
import { Mitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';

describe('Component Tests', () => {
  describe('Mitarbeiterskills Management Detail Component', () => {
    let comp: MitarbeiterskillsDetailComponent;
    let fixture: ComponentFixture<MitarbeiterskillsDetailComponent>;
    const route = ({ data: of({ mitarbeiterskills: new Mitarbeiterskills(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SkillmatrixTestModule],
        declarations: [MitarbeiterskillsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MitarbeiterskillsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MitarbeiterskillsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mitarbeiterskills on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mitarbeiterskills).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
