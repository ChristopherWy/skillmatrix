import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMitarbeiterskills, Mitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';
import { MitarbeiterskillsService } from './mitarbeiterskills.service';

@Component({
  selector: 'jhi-mitarbeiterskills-update',
  templateUrl: './mitarbeiterskills-update.component.html',
})
export class MitarbeiterskillsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.required]],
    skill: [null, [Validators.required]],
    level: [null, [Validators.required]],
  });

  constructor(
    protected mitarbeiterskillsService: MitarbeiterskillsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mitarbeiterskills }) => {
      this.updateForm(mitarbeiterskills);
    });
  }

  updateForm(mitarbeiterskills: IMitarbeiterskills): void {
    this.editForm.patchValue({
      id: mitarbeiterskills.id,
      email: mitarbeiterskills.email,
      skill: mitarbeiterskills.skill,
      level: mitarbeiterskills.level,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mitarbeiterskills = this.createFromForm();
    if (mitarbeiterskills.id !== undefined) {
      this.subscribeToSaveResponse(this.mitarbeiterskillsService.update(mitarbeiterskills));
    } else {
      this.subscribeToSaveResponse(this.mitarbeiterskillsService.create(mitarbeiterskills));
    }
  }

  private createFromForm(): IMitarbeiterskills {
    return {
      ...new Mitarbeiterskills(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      skill: this.editForm.get(['skill'])!.value,
      level: this.editForm.get(['level'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMitarbeiterskills>>): void {
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
