import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMitarbeiterskills, Mitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';
import { MitarbeiterskillsService } from './mitarbeiterskills.service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';
import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';
import { MitarbeiterService } from 'app/entities/mitarbeiter/mitarbeiter.service';

type SelectableEntity = ISkill | IMitarbeiter;

@Component({
  selector: 'jhi-mitarbeiterskills-update',
  templateUrl: './mitarbeiterskills-update.component.html',
})
export class MitarbeiterskillsUpdateComponent implements OnInit {
  isSaving = false;
  skills: ISkill[] = [];
  mitarbeiters: IMitarbeiter[] = [];

  editForm = this.fb.group({
    id: [],
    level: [null, [Validators.required]],
    skill: [],
    email: [],
  });

  constructor(
    protected mitarbeiterskillsService: MitarbeiterskillsService,
    protected skillService: SkillService,
    protected mitarbeiterService: MitarbeiterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mitarbeiterskills }) => {
      this.updateForm(mitarbeiterskills);

      this.skillService.query().subscribe((res: HttpResponse<ISkill[]>) => (this.skills = res.body || []));

      this.mitarbeiterService.query().subscribe((res: HttpResponse<IMitarbeiter[]>) => (this.mitarbeiters = res.body || []));
    });
  }

  updateForm(mitarbeiterskills: IMitarbeiterskills): void {
    this.editForm.patchValue({
      id: mitarbeiterskills.id,
      level: mitarbeiterskills.level,
      skill: mitarbeiterskills.skill,
      email: mitarbeiterskills.email,
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
      level: this.editForm.get(['level'])!.value,
      skill: this.editForm.get(['skill'])!.value,
      email: this.editForm.get(['email'])!.value,
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
