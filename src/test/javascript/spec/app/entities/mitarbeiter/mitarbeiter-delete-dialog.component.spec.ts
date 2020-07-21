import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SkillmatrixTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MitarbeiterDeleteDialogComponent } from 'app/entities/mitarbeiter/mitarbeiter-delete-dialog.component';
import { MitarbeiterService } from 'app/entities/mitarbeiter/mitarbeiter.service';

describe('Component Tests', () => {
  describe('Mitarbeiter Management Delete Component', () => {
    let comp: MitarbeiterDeleteDialogComponent;
    let fixture: ComponentFixture<MitarbeiterDeleteDialogComponent>;
    let service: MitarbeiterService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SkillmatrixTestModule],
        declarations: [MitarbeiterDeleteDialogComponent],
      })
        .overrideTemplate(MitarbeiterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MitarbeiterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MitarbeiterService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
