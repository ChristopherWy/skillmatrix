import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArbeitszeiten, Arbeitszeiten } from 'app/shared/model/arbeitszeiten.model';
import { ArbeitszeitenService } from './arbeitszeiten.service';

@Component({
  selector: 'jhi-arbeitszeiten-update',
  templateUrl: './arbeitszeiten-update.component.html',
})
export class ArbeitszeitenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.required]],
    wochenstunden: [null, [Validators.required]],
  });

  constructor(protected arbeitszeitenService: ArbeitszeitenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arbeitszeiten }) => {
      this.updateForm(arbeitszeiten);
    });
  }

  updateForm(arbeitszeiten: IArbeitszeiten): void {
    this.editForm.patchValue({
      id: arbeitszeiten.id,
      email: arbeitszeiten.email,
      wochenstunden: arbeitszeiten.wochenstunden,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arbeitszeiten = this.createFromForm();
    if (arbeitszeiten.id !== undefined) {
      this.subscribeToSaveResponse(this.arbeitszeitenService.update(arbeitszeiten));
    } else {
      this.subscribeToSaveResponse(this.arbeitszeitenService.create(arbeitszeiten));
    }
  }

  private createFromForm(): IArbeitszeiten {
    return {
      ...new Arbeitszeiten(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      wochenstunden: this.editForm.get(['wochenstunden'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArbeitszeiten>>): void {
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
