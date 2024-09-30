import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIntroduceArticleGroup, NewIntroduceArticleGroup } from '../introduce-article-group.model';

export type PartialUpdateIntroduceArticleGroup = Partial<IIntroduceArticleGroup> & Pick<IIntroduceArticleGroup, 'id'>;

export type EntityResponseType = HttpResponse<IIntroduceArticleGroup>;
export type EntityArrayResponseType = HttpResponse<IIntroduceArticleGroup[]>;

@Injectable({ providedIn: 'root' })
export class IntroduceArticleGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/introduce-article-groups');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(introduceArticleGroup: NewIntroduceArticleGroup): Observable<EntityResponseType> {
    return this.http.post<IIntroduceArticleGroup>(this.resourceUrl, introduceArticleGroup, { observe: 'response' });
  }

  update(introduceArticleGroup: IIntroduceArticleGroup): Observable<EntityResponseType> {
    return this.http.put<IIntroduceArticleGroup>(
      `${this.resourceUrl}/${this.getIntroduceArticleGroupIdentifier(introduceArticleGroup)}`,
      introduceArticleGroup,
      { observe: 'response' },
    );
  }

  partialUpdate(introduceArticleGroup: PartialUpdateIntroduceArticleGroup): Observable<EntityResponseType> {
    return this.http.patch<IIntroduceArticleGroup>(
      `${this.resourceUrl}/${this.getIntroduceArticleGroupIdentifier(introduceArticleGroup)}`,
      introduceArticleGroup,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIntroduceArticleGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIntroduceArticleGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIntroduceArticleGroupIdentifier(introduceArticleGroup: Pick<IIntroduceArticleGroup, 'id'>): number {
    return introduceArticleGroup.id;
  }

  compareIntroduceArticleGroup(o1: Pick<IIntroduceArticleGroup, 'id'> | null, o2: Pick<IIntroduceArticleGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getIntroduceArticleGroupIdentifier(o1) === this.getIntroduceArticleGroupIdentifier(o2) : o1 === o2;
  }

  addIntroduceArticleGroupToCollectionIfMissing<Type extends Pick<IIntroduceArticleGroup, 'id'>>(
    introduceArticleGroupCollection: Type[],
    ...introduceArticleGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const introduceArticleGroups: Type[] = introduceArticleGroupsToCheck.filter(isPresent);
    if (introduceArticleGroups.length > 0) {
      const introduceArticleGroupCollectionIdentifiers = introduceArticleGroupCollection.map(
        introduceArticleGroupItem => this.getIntroduceArticleGroupIdentifier(introduceArticleGroupItem)!,
      );
      const introduceArticleGroupsToAdd = introduceArticleGroups.filter(introduceArticleGroupItem => {
        const introduceArticleGroupIdentifier = this.getIntroduceArticleGroupIdentifier(introduceArticleGroupItem);
        if (introduceArticleGroupCollectionIdentifiers.includes(introduceArticleGroupIdentifier)) {
          return false;
        }
        introduceArticleGroupCollectionIdentifiers.push(introduceArticleGroupIdentifier);
        return true;
      });
      return [...introduceArticleGroupsToAdd, ...introduceArticleGroupCollection];
    }
    return introduceArticleGroupCollection;
  }
}
