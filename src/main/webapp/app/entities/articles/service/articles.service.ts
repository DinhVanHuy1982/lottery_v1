import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArticles, NewArticles } from '../articles.model';

export type PartialUpdateArticles = Partial<IArticles> & Pick<IArticles, 'id'>;

export type EntityResponseType = HttpResponse<IArticles>;
export type EntityArrayResponseType = HttpResponse<IArticles[]>;

@Injectable({ providedIn: 'root' })
export class ArticlesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api');
  private FUNCTION_ARTICLE = '/QLBV';

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(articles: NewArticles): Observable<EntityResponseType> {
    return this.http.post<IArticles>(this.resourceUrl, articles, { observe: 'response' });
  }

  update(articles: IArticles): Observable<EntityResponseType> {
    return this.http.put<IArticles>(`${this.resourceUrl}/${this.getArticlesIdentifier(articles)}`, articles, { observe: 'response' });
  }

  partialUpdate(articles: PartialUpdateArticles): Observable<EntityResponseType> {
    return this.http.patch<IArticles>(`${this.resourceUrl}/${this.getArticlesIdentifier(articles)}`, articles, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArticles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArticles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArticlesIdentifier(articles: Pick<IArticles, 'id'>): number {
    return articles.id;
  }

  compareArticles(o1: Pick<IArticles, 'id'> | null, o2: Pick<IArticles, 'id'> | null): boolean {
    return o1 && o2 ? this.getArticlesIdentifier(o1) === this.getArticlesIdentifier(o2) : o1 === o2;
  }

  addArticlesToCollectionIfMissing<Type extends Pick<IArticles, 'id'>>(
    articlesCollection: Type[],
    ...articlesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const articles: Type[] = articlesToCheck.filter(isPresent);
    if (articles.length > 0) {
      const articlesCollectionIdentifiers = articlesCollection.map(articlesItem => this.getArticlesIdentifier(articlesItem)!);
      const articlesToAdd = articles.filter(articlesItem => {
        const articlesIdentifier = this.getArticlesIdentifier(articlesItem);
        if (articlesCollectionIdentifiers.includes(articlesIdentifier)) {
          return false;
        }
        articlesCollectionIdentifiers.push(articlesIdentifier);
        return true;
      });
      return [...articlesToAdd, ...articlesCollection];
    }
    return articlesCollection;
  }

  searchArticle(dataSearch: any): Observable<any> {
    return this.http.post(this.resourceUrl + this.FUNCTION_ARTICLE + '/search/search-article', dataSearch);
  }
}
