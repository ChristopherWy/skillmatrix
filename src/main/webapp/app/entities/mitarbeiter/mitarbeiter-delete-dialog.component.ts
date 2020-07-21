import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';
import { MitarbeiterService } from './mitarbeiter.service';

@Component({
  templateUrl: './mitarbeiter-delete-dialog.component.html',
})
export class MitarbeiterDeleteDialogComponent {
  mitarbeiter?: IMitarbeiter;

  constructor(
    protected mitarbeiterService: MitarbeiterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mitarbeiterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mitarbeiterListModification');
      this.activeModal.close();
    });
  }
}
