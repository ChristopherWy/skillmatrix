import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';
import { MitarbeiterskillsService } from './mitarbeiterskills.service';

@Component({
  templateUrl: './mitarbeiterskills-delete-dialog.component.html',
})
export class MitarbeiterskillsDeleteDialogComponent {
  mitarbeiterskills?: IMitarbeiterskills;

  constructor(
    protected mitarbeiterskillsService: MitarbeiterskillsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mitarbeiterskillsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mitarbeiterskillsListModification');
      this.activeModal.close();
    });
  }
}
