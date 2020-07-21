import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArbeitszeiten } from 'app/shared/model/arbeitszeiten.model';
import { ArbeitszeitenService } from './arbeitszeiten.service';

@Component({
  templateUrl: './arbeitszeiten-delete-dialog.component.html',
})
export class ArbeitszeitenDeleteDialogComponent {
  arbeitszeiten?: IArbeitszeiten;

  constructor(
    protected arbeitszeitenService: ArbeitszeitenService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arbeitszeitenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('arbeitszeitenListModification');
      this.activeModal.close();
    });
  }
}
