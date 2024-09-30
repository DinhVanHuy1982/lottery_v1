import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRoleFunctionAction, NewRoleFunctionAction } from '../role-function-action.model';

export type PartialUpdateRoleFunctionAction = Partial<IRoleFunctionAction> & Pick<IRoleFunctionAction, 'id'>;

export type EntityResponseType = HttpResponse<IRoleFunctionAction>;
export type EntityArrayResponseType = HttpResponse<IRoleFunctionAction[]>;

@Injectable({ providedIn: 'root' })
export class RoleFunctionActionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/role-function-actions');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(roleFunctionAction: NewRoleFunctionAction): Observable<EntityResponseType> {
    return this.http.post<IRoleFunctionAction>(this.resourceUrl, roleFunctionAction, { observe: 'response' });
  }

  update(roleFunctionAction: IRoleFunctionAction): Observable<EntityResponseType> {
    return this.http.put<IRoleFunctionAction>(
      `${this.resourceUrl}/${this.getRoleFunctionActionIdentifier(roleFunctionAction)}`,
      roleFunctionAction,
      { observe: 'response' },
    );
  }

  partialUpdate(roleFunctionAction: PartialUpdateRoleFunctionAction): Observable<EntityResponseType> {
    return this.http.patch<IRoleFunctionAction>(
      `${this.resourceUrl}/${this.getRoleFunctionActionIdentifier(roleFunctionAction)}`,
      roleFunctionAction,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRoleFunctionAction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRoleFunctionAction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRoleFunctionActionIdentifier(roleFunctionAction: Pick<IRoleFunctionAction, 'id'>): number {
    return roleFunctionAction.id;
  }

  compareRoleFunctionAction(o1: Pick<IRoleFunctionAction, 'id'> | null, o2: Pick<IRoleFunctionAction, 'id'> | null): boolean {
    return o1 && o2 ? this.getRoleFunctionActionIdentifier(o1) === this.getRoleFunctionActionIdentifier(o2) : o1 === o2;
  }

  addRoleFunctionActionToCollectionIfMissing<Type extends Pick<IRoleFunctionAction, 'id'>>(
    roleFunctionActionCollection: Type[],
    ...roleFunctionActionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const roleFunctionActions: Type[] = roleFunctionActionsToCheck.filter(isPresent);
    if (roleFunctionActions.length > 0) {
      const roleFunctionActionCollectionIdentifiers = roleFunctionActionCollection.map(
        roleFunctionActionItem => this.getRoleFunctionActionIdentifier(roleFunctionActionItem)!,
      );
      const roleFunctionActionsToAdd = roleFunctionActions.filter(roleFunctionActionItem => {
        const roleFunctionActionIdentifier = this.getRoleFunctionActionIdentifier(roleFunctionActionItem);
        if (roleFunctionActionCollectionIdentifiers.includes(roleFunctionActionIdentifier)) {
          return false;
        }
        roleFunctionActionCollectionIdentifiers.push(roleFunctionActionIdentifier);
        return true;
      });
      return [...roleFunctionActionsToAdd, ...roleFunctionActionCollection];
    }
    return roleFunctionActionCollection;
  }
}
