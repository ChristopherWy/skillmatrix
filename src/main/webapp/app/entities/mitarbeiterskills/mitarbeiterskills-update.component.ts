import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMitarbeiterskills, Mitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';
import { MitarbeiterskillsService } from './mitarbeiterskills.service';
import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';
import { MitarbeiterService } from 'app/entities/mitarbeiter/mitarbeiter.service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';

type SelectableEntity = IMitarbeiter | ISkill;

@Component({
  selector: 'jhi-mitarbeiterskills-update',
  templateUrl: './mitarbeiterskills-update.component.html',
})
export class MitarbeiterskillsUpdateComponent implements OnInit {
  isSaving = false;
  mitarbeiters: IMitarbeiter[] = [];
  skills: ISkill[] = [];

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.required]],
    skill: [null, [Validators.required]],
    level: [null, [Validators.required]],
    email: [],
    skill: [],
    email: [],
    skill: [],
  });

  constructor(
    protected mitarbeiterskillsService: MitarbeiterskillsService,
    protected mitarbeiterService: MitarbeiterService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mitarbeiterskills }) => {
      this.updateForm(mitarbeiterskills);

      this.mitarbeiterService.query().subscribe((res: HttpResponse<IMitarbeiter[]>) => (this.mitarbeiters = res.body || []));

      this.skillService.query().subscribe((res: HttpResponse<ISkill[]>) => (this.skills = res.body || []));
    });
  }

  updateForm(mitarbeiterskills: IMitarbeiterskills): void {
    this.editForm.patchValue({
      id: mitarbeiterskills.id,
      email: mitarbeiterskills.email,
      skill: mitarbeiterskills.skill,
      level: mitarbeiterskills.level,
      email: mitarbeiterskills.email,
      skill: mitarbeiterskills.skill,
      email: mitarbeiterskills.email,
      skill: mitarbeiterskills.skill,
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
      email: this.editForm.get(['email'])!.value,
      skill: this.editForm.get(['skill'])!.value,
      email: this.editForm.get(['email'])!.value,
      skill: this.editForm.get(['skill'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
