import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActions, NewActions } from '../actions.model';

export type PartialUpdateActions = Partial<IActions> & Pick<IActions, 'id'>;

export type EntityResponseType = HttpResponse<IActions>;
export type EntityArrayResponseType = HttpResponse<IActions[]>;

@Injectable({ providedIn: 'root' })
export class ActionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/actions');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(actions: NewActions): Observable<EntityResponseType> {
    return this.http.post<IActions>(this.resourceUrl, actions, { observe: 'response' });
  }

  update(actions: IActions): Observable<EntityResponseType> {
    return this.http.put<IActions>(`${this.resourceUrl}/${this.getActionsIdentifier(actions)}`, actions, { observe: 'response' });
  }

  partialUpdate(actions: PartialUpdateActions): Observable<EntityResponseType> {
    return this.http.patch<IActions>(`${this.resourceUrl}/${this.getActionsIdentifier(actions)}`, actions, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getActionsIdentifier(actions: Pick<IActions, 'id'>): number {
    return actions.id;
  }

  compareActions(o1: Pick<IActions, 'id'> | null, o2: Pick<IActions, 'id'> | null): boolean {
    return o1 && o2 ? this.getActionsIdentifier(o1) === this.getActionsIdentifier(o2) : o1 === o2;
  }

  addActionsToCollectionIfMissing<Type extends Pick<IActions, 'id'>>(
    actionsCollection: Type[],
    ...actionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const actions: Type[] = actionsToCheck.filter(isPresent);
    if (actions.length > 0) {
      const actionsCollectionIdentifiers = actionsCollection.map(actionsItem => this.getActionsIdentifier(actionsItem)!);
      const actionsToAdd = actions.filter(actionsItem => {
        const actionsIdentifier = this.getActionsIdentifier(actionsItem);
        if (actionsCollectionIdentifiers.includes(actionsIdentifier)) {
          return false;
        }
        actionsCollectionIdentifiers.push(actionsIdentifier);
        return true;
      });
      return [...actionsToAdd, ...actionsCollection];
    }
    return actionsCollection;
  }
}
