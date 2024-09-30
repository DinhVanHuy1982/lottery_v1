import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFunctions, NewFunctions } from '../functions.model';

export type PartialUpdateFunctions = Partial<IFunctions> & Pick<IFunctions, 'id'>;

export type EntityResponseType = HttpResponse<IFunctions>;
export type EntityArrayResponseType = HttpResponse<IFunctions[]>;

@Injectable({ providedIn: 'root' })
export class FunctionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/functions');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(functions: NewFunctions): Observable<EntityResponseType> {
    return this.http.post<IFunctions>(this.resourceUrl, functions, { observe: 'response' });
  }

  update(functions: IFunctions): Observable<EntityResponseType> {
    return this.http.put<IFunctions>(`${this.resourceUrl}/${this.getFunctionsIdentifier(functions)}`, functions, { observe: 'response' });
  }

  partialUpdate(functions: PartialUpdateFunctions): Observable<EntityResponseType> {
    return this.http.patch<IFunctions>(`${this.resourceUrl}/${this.getFunctionsIdentifier(functions)}`, functions, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFunctions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFunctions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFunctionsIdentifier(functions: Pick<IFunctions, 'id'>): number {
    return functions.id;
  }

  compareFunctions(o1: Pick<IFunctions, 'id'> | null, o2: Pick<IFunctions, 'id'> | null): boolean {
    return o1 && o2 ? this.getFunctionsIdentifier(o1) === this.getFunctionsIdentifier(o2) : o1 === o2;
  }

  addFunctionsToCollectionIfMissing<Type extends Pick<IFunctions, 'id'>>(
    functionsCollection: Type[],
    ...functionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const functions: Type[] = functionsToCheck.filter(isPresent);
    if (functions.length > 0) {
      const functionsCollectionIdentifiers = functionsCollection.map(functionsItem => this.getFunctionsIdentifier(functionsItem)!);
      const functionsToAdd = functions.filter(functionsItem => {
        const functionsIdentifier = this.getFunctionsIdentifier(functionsItem);
        if (functionsCollectionIdentifiers.includes(functionsIdentifier)) {
          return false;
        }
        functionsCollectionIdentifiers.push(functionsIdentifier);
        return true;
      });
      return [...functionsToAdd, ...functionsCollection];
    }
    return functionsCollection;
  }
}
