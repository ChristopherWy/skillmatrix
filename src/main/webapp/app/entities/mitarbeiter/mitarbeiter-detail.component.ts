import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';

@Component({
  selector: 'jhi-mitarbeiter-detail',
  templateUrl: './mitarbeiter-detail.component.html',
})
export class MitarbeiterDetailComponent implements OnInit {
  mitarbeiter: IMitarbeiter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mitarbeiter }) => (this.mitarbeiter = mitarbeiter));
  }

  previousState(): void {
    window.history.back();
  }
}
