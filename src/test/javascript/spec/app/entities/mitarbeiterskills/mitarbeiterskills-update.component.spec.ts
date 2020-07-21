import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SkillmatrixTestModule } from '../../../test.module';
import { MitarbeiterskillsUpdateComponent } from 'app/entities/mitarbeiterskills/mitarbeiterskills-update.component';
import { MitarbeiterskillsService } from 'app/entities/mitarbeiterskills/mitarbeiterskills.service';
import { Mitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';

describe('Component Tests', () => {
  describe('Mitarbeiterskills Management Update Component', () => {
    let comp: MitarbeiterskillsUpdateComponent;
    let fixture: ComponentFixture<MitarbeiterskillsUpdateComponent>;
    let service: MitarbeiterskillsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SkillmatrixTestModule],
        declarations: [MitarbeiterskillsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MitarbeiterskillsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MitarbeiterskillsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MitarbeiterskillsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mitarbeiterskills(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mitarbeiterskills();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
