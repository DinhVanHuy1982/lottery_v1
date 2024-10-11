import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILevelDepositsResult, NewLevelDepositsResult } from '../level-deposits-result.model';

export type PartialUpdateLevelDepositsResult = Partial<ILevelDepositsResult> & Pick<ILevelDepositsResult, 'id'>;

type RestOf<T extends ILevelDepositsResult | NewLevelDepositsResult> = Omit<T, 'resultDate'> & {
  resultDate?: string | null;
};

export type RestLevelDepositsResult = RestOf<ILevelDepositsResult>;

export type NewRestLevelDepositsResult = RestOf<NewLevelDepositsResult>;

export type PartialUpdateRestLevelDepositsResult = RestOf<PartialUpdateLevelDepositsResult>;

export type EntityResponseType = HttpResponse<ILevelDepositsResult>;
export type EntityArrayResponseType = HttpResponse<ILevelDepositsResult[]>;

@Injectable({ providedIn: 'root' })
export class LevelDepositsResultService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/level-deposits-results');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(levelDepositsResult: NewLevelDepositsResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(levelDepositsResult);
    return this.http
      .post<RestLevelDepositsResult>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(levelDepositsResult: ILevelDepositsResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(levelDepositsResult);
    return this.http
      .put<RestLevelDepositsResult>(`${this.resourceUrl}/${this.getLevelDepositsResultIdentifier(levelDepositsResult)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(levelDepositsResult: PartialUpdateLevelDepositsResult): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(levelDepositsResult);
    return this.http
      .patch<RestLevelDepositsResult>(`${this.resourceUrl}/${this.getLevelDepositsResultIdentifier(levelDepositsResult)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLevelDepositsResult>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLevelDepositsResult[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLevelDepositsResultIdentifier(levelDepositsResult: Pick<ILevelDepositsResult, 'id'>): number {
    return levelDepositsResult.id;
  }

  compareLevelDepositsResult(o1: Pick<ILevelDepositsResult, 'id'> | null, o2: Pick<ILevelDepositsResult, 'id'> | null): boolean {
    return o1 && o2 ? this.getLevelDepositsResultIdentifier(o1) === this.getLevelDepositsResultIdentifier(o2) : o1 === o2;
  }

  addLevelDepositsResultToCollectionIfMissing<Type extends Pick<ILevelDepositsResult, 'id'>>(
    levelDepositsResultCollection: Type[],
    ...levelDepositsResultsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const levelDepositsResults: Type[] = levelDepositsResultsToCheck.filter(isPresent);
    if (levelDepositsResults.length > 0) {
      const levelDepositsResultCollectionIdentifiers = levelDepositsResultCollection.map(
        levelDepositsResultItem => this.getLevelDepositsResultIdentifier(levelDepositsResultItem)!,
      );
      const levelDepositsResultsToAdd = levelDepositsResults.filter(levelDepositsResultItem => {
        const levelDepositsResultIdentifier = this.getLevelDepositsResultIdentifier(levelDepositsResultItem);
        if (levelDepositsResultCollectionIdentifiers.includes(levelDepositsResultIdentifier)) {
          return false;
        }
        levelDepositsResultCollectionIdentifiers.push(levelDepositsResultIdentifier);
        return true;
      });
      return [...levelDepositsResultsToAdd, ...levelDepositsResultCollection];
    }
    return levelDepositsResultCollection;
  }

  protected convertDateFromClient<T extends ILevelDepositsResult | NewLevelDepositsResult | PartialUpdateLevelDepositsResult>(
    levelDepositsResult: T,
  ): RestOf<T> {
    return {
      ...levelDepositsResult,
      resultDate: levelDepositsResult.resultDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLevelDepositsResult: RestLevelDepositsResult): ILevelDepositsResult {
    return {
      ...restLevelDepositsResult,
      resultDate: restLevelDepositsResult.resultDate ? dayjs(restLevelDepositsResult.resultDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLevelDepositsResult>): HttpResponse<ILevelDepositsResult> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLevelDepositsResult[]>): HttpResponse<ILevelDepositsResult[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
