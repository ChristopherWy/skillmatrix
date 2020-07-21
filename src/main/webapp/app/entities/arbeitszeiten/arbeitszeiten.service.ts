import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArbeitszeiten } from 'app/shared/model/arbeitszeiten.model';

type EntityResponseType = HttpResponse<IArbeitszeiten>;
type EntityArrayResponseType = HttpResponse<IArbeitszeiten[]>;

@Injectable({ providedIn: 'root' })
export class ArbeitszeitenService {
  public resourceUrl = SERVER_API_URL + 'api/arbeitszeitens';

  constructor(protected http: HttpClient) {}

  create(arbeitszeiten: IArbeitszeiten): Observable<EntityResponseType> {
    return this.http.post<IArbeitszeiten>(this.resourceUrl, arbeitszeiten, { observe: 'response' });
  }

  update(arbeitszeiten: IArbeitszeiten): Observable<EntityResponseType> {
    return this.http.put<IArbeitszeiten>(this.resourceUrl, arbeitszeiten, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArbeitszeiten>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArbeitszeiten[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
