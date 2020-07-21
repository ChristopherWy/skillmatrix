import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMitarbeiter, Mitarbeiter } from 'app/shared/model/mitarbeiter.model';
import { MitarbeiterService } from './mitarbeiter.service';

@Component({
  selector: 'jhi-mitarbeiter-update',
  templateUrl: './mitarbeiter-update.component.html',
})
export class MitarbeiterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.required]],
    vorname: [],
    nachname: [],
    intern: [],
  });

  constructor(protected mitarbeiterService: MitarbeiterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mitarbeiter }) => {
      this.updateForm(mitarbeiter);
    });
  }

  updateForm(mitarbeiter: IMitarbeiter): void {
    this.editForm.patchValue({
      id: mitarbeiter.id,
      email: mitarbeiter.email,
      vorname: mitarbeiter.vorname,
      nachname: mitarbeiter.nachname,
      intern: mitarbeiter.intern,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mitarbeiter = this.createFromForm();
    if (mitarbeiter.id !== undefined) {
      this.subscribeToSaveResponse(this.mitarbeiterService.update(mitarbeiter));
    } else {
      this.subscribeToSaveResponse(this.mitarbeiterService.create(mitarbeiter));
    }
  }

  private createFromForm(): IMitarbeiter {
    return {
      ...new Mitarbeiter(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      vorname: this.editForm.get(['vorname'])!.value,
      nachname: this.editForm.get(['nachname'])!.value,
      intern: this.editForm.get(['intern'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMitarbeiter>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
