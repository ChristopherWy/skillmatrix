import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArbeitszeiten, Arbeitszeiten } from 'app/shared/model/arbeitszeiten.model';
import { ArbeitszeitenService } from './arbeitszeiten.service';
import { ArbeitszeitenComponent } from './arbeitszeiten.component';
import { ArbeitszeitenDetailComponent } from './arbeitszeiten-detail.component';
import { ArbeitszeitenUpdateComponent } from './arbeitszeiten-update.component';

@Injectable({ providedIn: 'root' })
export class ArbeitszeitenResolve implements Resolve<IArbeitszeiten> {
  constructor(private service: ArbeitszeitenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArbeitszeiten> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((arbeitszeiten: HttpResponse<Arbeitszeiten>) => {
          if (arbeitszeiten.body) {
            return of(arbeitszeiten.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Arbeitszeiten());
  }
}

export const arbeitszeitenRoute: Routes = [
  {
    path: '',
    component: ArbeitszeitenComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Arbeitszeitens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArbeitszeitenDetailComponent,
    resolve: {
      arbeitszeiten: ArbeitszeitenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Arbeitszeitens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArbeitszeitenUpdateComponent,
    resolve: {
      arbeitszeiten: ArbeitszeitenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Arbeitszeitens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArbeitszeitenUpdateComponent,
    resolve: {
      arbeitszeiten: ArbeitszeitenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Arbeitszeitens',
    },
    canActivate: [UserRouteAccessService],
  },
];
