import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';

@Component({
  selector: 'jhi-mitarbeiterskills-detail',
  templateUrl: './mitarbeiterskills-detail.component.html',
})
export class MitarbeiterskillsDetailComponent implements OnInit {
  mitarbeiterskills: IMitarbeiterskills | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mitarbeiterskills }) => (this.mitarbeiterskills = mitarbeiterskills));
  }

  previousState(): void {
    window.history.back();
  }
}
