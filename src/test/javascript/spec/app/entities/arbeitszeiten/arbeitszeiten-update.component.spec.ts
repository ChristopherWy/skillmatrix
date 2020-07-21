import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SkillmatrixTestModule } from '../../../test.module';
import { ArbeitszeitenUpdateComponent } from 'app/entities/arbeitszeiten/arbeitszeiten-update.component';
import { ArbeitszeitenService } from 'app/entities/arbeitszeiten/arbeitszeiten.service';
import { Arbeitszeiten } from 'app/shared/model/arbeitszeiten.model';

describe('Component Tests', () => {
  describe('Arbeitszeiten Management Update Component', () => {
    let comp: ArbeitszeitenUpdateComponent;
    let fixture: ComponentFixture<ArbeitszeitenUpdateComponent>;
    let service: ArbeitszeitenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SkillmatrixTestModule],
        declarations: [ArbeitszeitenUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArbeitszeitenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArbeitszeitenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArbeitszeitenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Arbeitszeiten(123);
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
        const entity = new Arbeitszeiten();
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
