import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArticleGroup, NewArticleGroup } from '../article-group.model';

export type PartialUpdateArticleGroup = Partial<IArticleGroup> & Pick<IArticleGroup, 'id'>;

type RestOf<T extends IArticleGroup | NewArticleGroup> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

export type RestArticleGroup = RestOf<IArticleGroup>;

export type NewRestArticleGroup = RestOf<NewArticleGroup>;

export type PartialUpdateRestArticleGroup = RestOf<PartialUpdateArticleGroup>;

export type EntityResponseType = HttpResponse<IArticleGroup>;
export type EntityArrayResponseType = HttpResponse<IArticleGroup[]>;

@Injectable({ providedIn: 'root' })
export class ArticleGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/');
  private FUNCTION_QLNVB = 'QLNVB';

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(articleGroup: NewArticleGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articleGroup);
    return this.http
      .post<RestArticleGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(articleGroup: IArticleGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articleGroup);
    return this.http
      .put<RestArticleGroup>(`${this.resourceUrl}/${this.getArticleGroupIdentifier(articleGroup)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(articleGroup: PartialUpdateArticleGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(articleGroup);
    return this.http
      .patch<RestArticleGroup>(`${this.resourceUrl}/${this.getArticleGroupIdentifier(articleGroup)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestArticleGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestArticleGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArticleGroupIdentifier(articleGroup: Pick<IArticleGroup, 'id'>): number {
    return articleGroup.id;
  }

  compareArticleGroup(o1: Pick<IArticleGroup, 'id'> | null, o2: Pick<IArticleGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getArticleGroupIdentifier(o1) === this.getArticleGroupIdentifier(o2) : o1 === o2;
  }

  addArticleGroupToCollectionIfMissing<Type extends Pick<IArticleGroup, 'id'>>(
    articleGroupCollection: Type[],
    ...articleGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const articleGroups: Type[] = articleGroupsToCheck.filter(isPresent);
    if (articleGroups.length > 0) {
      const articleGroupCollectionIdentifiers = articleGroupCollection.map(
        articleGroupItem => this.getArticleGroupIdentifier(articleGroupItem)!,
      );
      const articleGroupsToAdd = articleGroups.filter(articleGroupItem => {
        const articleGroupIdentifier = this.getArticleGroupIdentifier(articleGroupItem);
        if (articleGroupCollectionIdentifiers.includes(articleGroupIdentifier)) {
          return false;
        }
        articleGroupCollectionIdentifiers.push(articleGroupIdentifier);
        return true;
      });
      return [...articleGroupsToAdd, ...articleGroupCollection];
    }
    return articleGroupCollection;
  }

  protected convertDateFromClient<T extends IArticleGroup | NewArticleGroup | PartialUpdateArticleGroup>(articleGroup: T): RestOf<T> {
    return {
      ...articleGroup,
      createTime: articleGroup.createTime?.toJSON() ?? null,
      updateTime: articleGroup.updateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restArticleGroup: RestArticleGroup): IArticleGroup {
    return {
      ...restArticleGroup,
      createTime: restArticleGroup.createTime ? dayjs(restArticleGroup.createTime) : undefined,
      updateTime: restArticleGroup.updateTime ? dayjs(restArticleGroup.updateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestArticleGroup>): HttpResponse<IArticleGroup> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestArticleGroup[]>): HttpResponse<IArticleGroup[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  getLstArticleGroupCodeAndName(): Observable<any> {
    return this.http.get(this.resourceUrl + this.FUNCTION_QLNVB + '/search/list-name-group');
  }
}
