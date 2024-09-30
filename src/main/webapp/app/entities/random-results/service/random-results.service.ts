import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRandomResults, NewRandomResults } from '../random-results.model';

export type PartialUpdateRandomResults = Partial<IRandomResults> & Pick<IRandomResults, 'id'>;

type RestOf<T extends IRandomResults | NewRandomResults> = Omit<T, 'randomDate'> & {
  randomDate?: string | null;
};

export type RestRandomResults = RestOf<IRandomResults>;

export type NewRestRandomResults = RestOf<NewRandomResults>;

export type PartialUpdateRestRandomResults = RestOf<PartialUpdateRandomResults>;

export type EntityResponseType = HttpResponse<IRandomResults>;
export type EntityArrayResponseType = HttpResponse<IRandomResults[]>;

@Injectable({ providedIn: 'root' })
export class RandomResultsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/random-results');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(randomResults: NewRandomResults): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(randomResults);
    return this.http
      .post<RestRandomResults>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(randomResults: IRandomResults): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(randomResults);
    return this.http
      .put<RestRandomResults>(`${this.resourceUrl}/${this.getRandomResultsIdentifier(randomResults)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(randomResults: PartialUpdateRandomResults): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(randomResults);
    return this.http
      .patch<RestRandomResults>(`${this.resourceUrl}/${this.getRandomResultsIdentifier(randomResults)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRandomResults>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRandomResults[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRandomResultsIdentifier(randomResults: Pick<IRandomResults, 'id'>): number {
    return randomResults.id;
  }

  compareRandomResults(o1: Pick<IRandomResults, 'id'> | null, o2: Pick<IRandomResults, 'id'> | null): boolean {
    return o1 && o2 ? this.getRandomResultsIdentifier(o1) === this.getRandomResultsIdentifier(o2) : o1 === o2;
  }

  addRandomResultsToCollectionIfMissing<Type extends Pick<IRandomResults, 'id'>>(
    randomResultsCollection: Type[],
    ...randomResultsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const randomResults: Type[] = randomResultsToCheck.filter(isPresent);
    if (randomResults.length > 0) {
      const randomResultsCollectionIdentifiers = randomResultsCollection.map(
        randomResultsItem => this.getRandomResultsIdentifier(randomResultsItem)!,
      );
      const randomResultsToAdd = randomResults.filter(randomResultsItem => {
        const randomResultsIdentifier = this.getRandomResultsIdentifier(randomResultsItem);
        if (randomResultsCollectionIdentifiers.includes(randomResultsIdentifier)) {
          return false;
        }
        randomResultsCollectionIdentifiers.push(randomResultsIdentifier);
        return true;
      });
      return [...randomResultsToAdd, ...randomResultsCollection];
    }
    return randomResultsCollection;
  }

  protected convertDateFromClient<T extends IRandomResults | NewRandomResults | PartialUpdateRandomResults>(randomResults: T): RestOf<T> {
    return {
      ...randomResults,
      randomDate: randomResults.randomDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRandomResults: RestRandomResults): IRandomResults {
    return {
      ...restRandomResults,
      randomDate: restRandomResults.randomDate ? dayjs(restRandomResults.randomDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRandomResults>): HttpResponse<IRandomResults> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRandomResults[]>): HttpResponse<IRandomResults[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
