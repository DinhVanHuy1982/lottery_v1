import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIntroduceArticle, NewIntroduceArticle } from '../introduce-article.model';

export type PartialUpdateIntroduceArticle = Partial<IIntroduceArticle> & Pick<IIntroduceArticle, 'id'>;

export type EntityResponseType = HttpResponse<IIntroduceArticle>;
export type EntityArrayResponseType = HttpResponse<IIntroduceArticle[]>;

@Injectable({ providedIn: 'root' })
export class IntroduceArticleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/introduce-articles');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(introduceArticle: NewIntroduceArticle): Observable<EntityResponseType> {
    return this.http.post<IIntroduceArticle>(this.resourceUrl, introduceArticle, { observe: 'response' });
  }

  update(introduceArticle: IIntroduceArticle): Observable<EntityResponseType> {
    return this.http.put<IIntroduceArticle>(
      `${this.resourceUrl}/${this.getIntroduceArticleIdentifier(introduceArticle)}`,
      introduceArticle,
      { observe: 'response' },
    );
  }

  partialUpdate(introduceArticle: PartialUpdateIntroduceArticle): Observable<EntityResponseType> {
    return this.http.patch<IIntroduceArticle>(
      `${this.resourceUrl}/${this.getIntroduceArticleIdentifier(introduceArticle)}`,
      introduceArticle,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIntroduceArticle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIntroduceArticle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIntroduceArticleIdentifier(introduceArticle: Pick<IIntroduceArticle, 'id'>): number {
    return introduceArticle.id;
  }

  compareIntroduceArticle(o1: Pick<IIntroduceArticle, 'id'> | null, o2: Pick<IIntroduceArticle, 'id'> | null): boolean {
    return o1 && o2 ? this.getIntroduceArticleIdentifier(o1) === this.getIntroduceArticleIdentifier(o2) : o1 === o2;
  }

  addIntroduceArticleToCollectionIfMissing<Type extends Pick<IIntroduceArticle, 'id'>>(
    introduceArticleCollection: Type[],
    ...introduceArticlesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const introduceArticles: Type[] = introduceArticlesToCheck.filter(isPresent);
    if (introduceArticles.length > 0) {
      const introduceArticleCollectionIdentifiers = introduceArticleCollection.map(
        introduceArticleItem => this.getIntroduceArticleIdentifier(introduceArticleItem)!,
      );
      const introduceArticlesToAdd = introduceArticles.filter(introduceArticleItem => {
        const introduceArticleIdentifier = this.getIntroduceArticleIdentifier(introduceArticleItem);
        if (introduceArticleCollectionIdentifiers.includes(introduceArticleIdentifier)) {
          return false;
        }
        introduceArticleCollectionIdentifiers.push(introduceArticleIdentifier);
        return true;
      });
      return [...introduceArticlesToAdd, ...introduceArticleCollection];
    }
    return introduceArticleCollection;
  }
}
