import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IArbeitszeiten, Arbeitszeiten } from 'app/shared/model/arbeitszeiten.model';
import { ArbeitszeitenService } from './arbeitszeiten.service';
import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';
import { MitarbeiterService } from 'app/entities/mitarbeiter/mitarbeiter.service';

@Component({
  selector: 'jhi-arbeitszeiten-update',
  templateUrl: './arbeitszeiten-update.component.html',
})
export class ArbeitszeitenUpdateComponent implements OnInit {
  isSaving = false;
  mitarbeiters: IMitarbeiter[] = [];

  editForm = this.fb.group({
    id: [],
    wochenstunden: [null, [Validators.required]],
    mitarbeiter: [],
  });

  constructor(
    protected arbeitszeitenService: ArbeitszeitenService,
    protected mitarbeiterService: MitarbeiterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arbeitszeiten }) => {
      this.updateForm(arbeitszeiten);

      this.mitarbeiterService
        .query({ filter: 'arbeitszeiten-is-null' })
        .pipe(
          map((res: HttpResponse<IMitarbeiter[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMitarbeiter[]) => {
          if (!arbeitszeiten.mitarbeiter || !arbeitszeiten.mitarbeiter.id) {
            this.mitarbeiters = resBody;
          } else {
            this.mitarbeiterService
              .find(arbeitszeiten.mitarbeiter.id)
              .pipe(
                map((subRes: HttpResponse<IMitarbeiter>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMitarbeiter[]) => (this.mitarbeiters = concatRes));
          }
        });
    });
  }

  updateForm(arbeitszeiten: IArbeitszeiten): void {
    this.editForm.patchValue({
      id: arbeitszeiten.id,
      wochenstunden: arbeitszeiten.wochenstunden,
      mitarbeiter: arbeitszeiten.mitarbeiter,
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
      wochenstunden: this.editForm.get(['wochenstunden'])!.value,
      mitarbeiter: this.editForm.get(['mitarbeiter'])!.value,
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

  trackById(index: number, item: IMitarbeiter): any {
    return item.id;
  }
}
