import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SkillmatrixTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MitarbeiterskillsDeleteDialogComponent } from 'app/entities/mitarbeiterskills/mitarbeiterskills-delete-dialog.component';
import { MitarbeiterskillsService } from 'app/entities/mitarbeiterskills/mitarbeiterskills.service';

describe('Component Tests', () => {
  describe('Mitarbeiterskills Management Delete Component', () => {
    let comp: MitarbeiterskillsDeleteDialogComponent;
    let fixture: ComponentFixture<MitarbeiterskillsDeleteDialogComponent>;
    let service: MitarbeiterskillsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SkillmatrixTestModule],
        declarations: [MitarbeiterskillsDeleteDialogComponent],
      })
        .overrideTemplate(MitarbeiterskillsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MitarbeiterskillsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MitarbeiterskillsService);
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
