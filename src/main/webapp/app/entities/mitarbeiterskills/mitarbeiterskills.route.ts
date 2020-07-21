import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMitarbeiterskills, Mitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';
import { MitarbeiterskillsService } from './mitarbeiterskills.service';
import { MitarbeiterskillsComponent } from './mitarbeiterskills.component';
import { MitarbeiterskillsDetailComponent } from './mitarbeiterskills-detail.component';
import { MitarbeiterskillsUpdateComponent } from './mitarbeiterskills-update.component';

@Injectable({ providedIn: 'root' })
export class MitarbeiterskillsResolve implements Resolve<IMitarbeiterskills> {
  constructor(private service: MitarbeiterskillsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMitarbeiterskills> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mitarbeiterskills: HttpResponse<Mitarbeiterskills>) => {
          if (mitarbeiterskills.body) {
            return of(mitarbeiterskills.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mitarbeiterskills());
  }
}

export const mitarbeiterskillsRoute: Routes = [
  {
    path: '',
    component: MitarbeiterskillsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Mitarbeiterskills',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MitarbeiterskillsDetailComponent,
    resolve: {
      mitarbeiterskills: MitarbeiterskillsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mitarbeiterskills',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MitarbeiterskillsUpdateComponent,
    resolve: {
      mitarbeiterskills: MitarbeiterskillsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mitarbeiterskills',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MitarbeiterskillsUpdateComponent,
    resolve: {
      mitarbeiterskills: MitarbeiterskillsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mitarbeiterskills',
    },
    canActivate: [UserRouteAccessService],
  },
];
