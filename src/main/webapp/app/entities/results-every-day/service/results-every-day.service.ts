import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResultsEveryDay, NewResultsEveryDay } from '../results-every-day.model';

export type PartialUpdateResultsEveryDay = Partial<IResultsEveryDay> & Pick<IResultsEveryDay, 'id'>;

type RestOf<T extends IResultsEveryDay | NewResultsEveryDay> = Omit<T, 'resultDate'> & {
  resultDate?: string | null;
};

export type RestResultsEveryDay = RestOf<IResultsEveryDay>;

export type NewRestResultsEveryDay = RestOf<NewResultsEveryDay>;

export type PartialUpdateRestResultsEveryDay = RestOf<PartialUpdateResultsEveryDay>;

export type EntityResponseType = HttpResponse<IResultsEveryDay>;
export type EntityArrayResponseType = HttpResponse<IResultsEveryDay[]>;

@Injectable({ providedIn: 'root' })
export class ResultsEveryDayService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/results-every-days');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(resultsEveryDay: NewResultsEveryDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultsEveryDay);
    return this.http
      .post<RestResultsEveryDay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(resultsEveryDay: IResultsEveryDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultsEveryDay);
    return this.http
      .put<RestResultsEveryDay>(`${this.resourceUrl}/${this.getResultsEveryDayIdentifier(resultsEveryDay)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(resultsEveryDay: PartialUpdateResultsEveryDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resultsEveryDay);
    return this.http
      .patch<RestResultsEveryDay>(`${this.resourceUrl}/${this.getResultsEveryDayIdentifier(resultsEveryDay)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResultsEveryDay>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResultsEveryDay[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResultsEveryDayIdentifier(resultsEveryDay: Pick<IResultsEveryDay, 'id'>): number {
    return resultsEveryDay.id;
  }

  compareResultsEveryDay(o1: Pick<IResultsEveryDay, 'id'> | null, o2: Pick<IResultsEveryDay, 'id'> | null): boolean {
    return o1 && o2 ? this.getResultsEveryDayIdentifier(o1) === this.getResultsEveryDayIdentifier(o2) : o1 === o2;
  }

  addResultsEveryDayToCollectionIfMissing<Type extends Pick<IResultsEveryDay, 'id'>>(
    resultsEveryDayCollection: Type[],
    ...resultsEveryDaysToCheck: (Type | null | undefined)[]
  ): Type[] {
    const resultsEveryDays: Type[] = resultsEveryDaysToCheck.filter(isPresent);
    if (resultsEveryDays.length > 0) {
      const resultsEveryDayCollectionIdentifiers = resultsEveryDayCollection.map(
        resultsEveryDayItem => this.getResultsEveryDayIdentifier(resultsEveryDayItem)!,
      );
      const resultsEveryDaysToAdd = resultsEveryDays.filter(resultsEveryDayItem => {
        const resultsEveryDayIdentifier = this.getResultsEveryDayIdentifier(resultsEveryDayItem);
        if (resultsEveryDayCollectionIdentifiers.includes(resultsEveryDayIdentifier)) {
          return false;
        }
        resultsEveryDayCollectionIdentifiers.push(resultsEveryDayIdentifier);
        return true;
      });
      return [...resultsEveryDaysToAdd, ...resultsEveryDayCollection];
    }
    return resultsEveryDayCollection;
  }

  protected convertDateFromClient<T extends IResultsEveryDay | NewResultsEveryDay | PartialUpdateResultsEveryDay>(
    resultsEveryDay: T,
  ): RestOf<T> {
    return {
      ...resultsEveryDay,
      resultDate: resultsEveryDay.resultDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restResultsEveryDay: RestResultsEveryDay): IResultsEveryDay {
    return {
      ...restResultsEveryDay,
      resultDate: restResultsEveryDay.resultDate ? dayjs(restResultsEveryDay.resultDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResultsEveryDay>): HttpResponse<IResultsEveryDay> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResultsEveryDay[]>): HttpResponse<IResultsEveryDay[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
