import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArbeitszeiten } from 'app/shared/model/arbeitszeiten.model';

@Component({
  selector: 'jhi-arbeitszeiten-detail',
  templateUrl: './arbeitszeiten-detail.component.html',
})
export class ArbeitszeitenDetailComponent implements OnInit {
  arbeitszeiten: IArbeitszeiten | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arbeitszeiten }) => (this.arbeitszeiten = arbeitszeiten));
  }

  previousState(): void {
    window.history.back();
  }
}
