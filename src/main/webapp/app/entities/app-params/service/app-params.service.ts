import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAppParams, NewAppParams } from '../app-params.model';

export type PartialUpdateAppParams = Partial<IAppParams> & Pick<IAppParams, 'id'>;

export type EntityResponseType = HttpResponse<IAppParams>;
export type EntityArrayResponseType = HttpResponse<IAppParams[]>;

@Injectable({ providedIn: 'root' })
export class AppParamsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/app-params');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(appParams: NewAppParams): Observable<EntityResponseType> {
    return this.http.post<IAppParams>(this.resourceUrl, appParams, { observe: 'response' });
  }

  update(appParams: IAppParams): Observable<EntityResponseType> {
    return this.http.put<IAppParams>(`${this.resourceUrl}/${this.getAppParamsIdentifier(appParams)}`, appParams, { observe: 'response' });
  }

  partialUpdate(appParams: PartialUpdateAppParams): Observable<EntityResponseType> {
    return this.http.patch<IAppParams>(`${this.resourceUrl}/${this.getAppParamsIdentifier(appParams)}`, appParams, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppParams>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppParams[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAppParamsIdentifier(appParams: Pick<IAppParams, 'id'>): number {
    return appParams.id;
  }

  compareAppParams(o1: Pick<IAppParams, 'id'> | null, o2: Pick<IAppParams, 'id'> | null): boolean {
    return o1 && o2 ? this.getAppParamsIdentifier(o1) === this.getAppParamsIdentifier(o2) : o1 === o2;
  }

  addAppParamsToCollectionIfMissing<Type extends Pick<IAppParams, 'id'>>(
    appParamsCollection: Type[],
    ...appParamsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const appParams: Type[] = appParamsToCheck.filter(isPresent);
    if (appParams.length > 0) {
      const appParamsCollectionIdentifiers = appParamsCollection.map(appParamsItem => this.getAppParamsIdentifier(appParamsItem)!);
      const appParamsToAdd = appParams.filter(appParamsItem => {
        const appParamsIdentifier = this.getAppParamsIdentifier(appParamsItem);
        if (appParamsCollectionIdentifiers.includes(appParamsIdentifier)) {
          return false;
        }
        appParamsCollectionIdentifiers.push(appParamsIdentifier);
        return true;
      });
      return [...appParamsToAdd, ...appParamsCollection];
    }
    return appParamsCollection;
  }
}
