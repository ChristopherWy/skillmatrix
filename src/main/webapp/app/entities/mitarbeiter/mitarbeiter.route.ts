import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMitarbeiter, Mitarbeiter } from 'app/shared/model/mitarbeiter.model';
import { MitarbeiterService } from './mitarbeiter.service';
import { MitarbeiterComponent } from './mitarbeiter.component';
import { MitarbeiterDetailComponent } from './mitarbeiter-detail.component';
import { MitarbeiterUpdateComponent } from './mitarbeiter-update.component';

@Injectable({ providedIn: 'root' })
export class MitarbeiterResolve implements Resolve<IMitarbeiter> {
  constructor(private service: MitarbeiterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMitarbeiter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mitarbeiter: HttpResponse<Mitarbeiter>) => {
          if (mitarbeiter.body) {
            return of(mitarbeiter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mitarbeiter());
  }
}

export const mitarbeiterRoute: Routes = [
  {
    path: '',
    component: MitarbeiterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Mitarbeiters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MitarbeiterDetailComponent,
    resolve: {
      mitarbeiter: MitarbeiterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mitarbeiters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MitarbeiterUpdateComponent,
    resolve: {
      mitarbeiter: MitarbeiterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mitarbeiters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MitarbeiterUpdateComponent,
    resolve: {
      mitarbeiter: MitarbeiterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mitarbeiters',
    },
    canActivate: [UserRouteAccessService],
  },
];
