import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRoleFunctions, NewRoleFunctions } from '../role-functions.model';

export type PartialUpdateRoleFunctions = Partial<IRoleFunctions> & Pick<IRoleFunctions, 'id'>;

export type EntityResponseType = HttpResponse<IRoleFunctions>;
export type EntityArrayResponseType = HttpResponse<IRoleFunctions[]>;

@Injectable({ providedIn: 'root' })
export class RoleFunctionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/role-functions');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(roleFunctions: NewRoleFunctions): Observable<EntityResponseType> {
    return this.http.post<IRoleFunctions>(this.resourceUrl, roleFunctions, { observe: 'response' });
  }

  update(roleFunctions: IRoleFunctions): Observable<EntityResponseType> {
    return this.http.put<IRoleFunctions>(`${this.resourceUrl}/${this.getRoleFunctionsIdentifier(roleFunctions)}`, roleFunctions, {
      observe: 'response',
    });
  }

  partialUpdate(roleFunctions: PartialUpdateRoleFunctions): Observable<EntityResponseType> {
    return this.http.patch<IRoleFunctions>(`${this.resourceUrl}/${this.getRoleFunctionsIdentifier(roleFunctions)}`, roleFunctions, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRoleFunctions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRoleFunctions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRoleFunctionsIdentifier(roleFunctions: Pick<IRoleFunctions, 'id'>): number {
    return roleFunctions.id;
  }

  compareRoleFunctions(o1: Pick<IRoleFunctions, 'id'> | null, o2: Pick<IRoleFunctions, 'id'> | null): boolean {
    return o1 && o2 ? this.getRoleFunctionsIdentifier(o1) === this.getRoleFunctionsIdentifier(o2) : o1 === o2;
  }

  addRoleFunctionsToCollectionIfMissing<Type extends Pick<IRoleFunctions, 'id'>>(
    roleFunctionsCollection: Type[],
    ...roleFunctionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const roleFunctions: Type[] = roleFunctionsToCheck.filter(isPresent);
    if (roleFunctions.length > 0) {
      const roleFunctionsCollectionIdentifiers = roleFunctionsCollection.map(
        roleFunctionsItem => this.getRoleFunctionsIdentifier(roleFunctionsItem)!,
      );
      const roleFunctionsToAdd = roleFunctions.filter(roleFunctionsItem => {
        const roleFunctionsIdentifier = this.getRoleFunctionsIdentifier(roleFunctionsItem);
        if (roleFunctionsCollectionIdentifiers.includes(roleFunctionsIdentifier)) {
          return false;
        }
        roleFunctionsCollectionIdentifiers.push(roleFunctionsIdentifier);
        return true;
      });
      return [...roleFunctionsToAdd, ...roleFunctionsCollection];
    }
    return roleFunctionsCollection;
  }
}
