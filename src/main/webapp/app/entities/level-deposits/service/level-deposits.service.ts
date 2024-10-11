import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILevelDeposits, NewLevelDeposits } from '../level-deposits.model';

export type PartialUpdateLevelDeposits = Partial<ILevelDeposits> & Pick<ILevelDeposits, 'id'>;

type RestOf<T extends ILevelDeposits | NewLevelDeposits> = Omit<T, 'updateTime'> & {
  updateTime?: string | null;
};

export type RestLevelDeposits = RestOf<ILevelDeposits>;

export type NewRestLevelDeposits = RestOf<NewLevelDeposits>;

export type PartialUpdateRestLevelDeposits = RestOf<PartialUpdateLevelDeposits>;

export type EntityResponseType = HttpResponse<ILevelDeposits>;
export type EntityArrayResponseType = HttpResponse<ILevelDeposits[]>;

@Injectable({ providedIn: 'root' })
export class LevelDepositsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/level-deposits');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(levelDeposits: NewLevelDeposits): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(levelDeposits);
    return this.http
      .post<RestLevelDeposits>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(levelDeposits: ILevelDeposits): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(levelDeposits);
    return this.http
      .put<RestLevelDeposits>(`${this.resourceUrl}/${this.getLevelDepositsIdentifier(levelDeposits)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(levelDeposits: PartialUpdateLevelDeposits): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(levelDeposits);
    return this.http
      .patch<RestLevelDeposits>(`${this.resourceUrl}/${this.getLevelDepositsIdentifier(levelDeposits)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLevelDeposits>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLevelDeposits[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLevelDepositsIdentifier(levelDeposits: Pick<ILevelDeposits, 'id'>): number {
    return levelDeposits.id;
  }

  compareLevelDeposits(o1: Pick<ILevelDeposits, 'id'> | null, o2: Pick<ILevelDeposits, 'id'> | null): boolean {
    return o1 && o2 ? this.getLevelDepositsIdentifier(o1) === this.getLevelDepositsIdentifier(o2) : o1 === o2;
  }

  addLevelDepositsToCollectionIfMissing<Type extends Pick<ILevelDeposits, 'id'>>(
    levelDepositsCollection: Type[],
    ...levelDepositsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const levelDeposits: Type[] = levelDepositsToCheck.filter(isPresent);
    if (levelDeposits.length > 0) {
      const levelDepositsCollectionIdentifiers = levelDepositsCollection.map(
        levelDepositsItem => this.getLevelDepositsIdentifier(levelDepositsItem)!,
      );
      const levelDepositsToAdd = levelDeposits.filter(levelDepositsItem => {
        const levelDepositsIdentifier = this.getLevelDepositsIdentifier(levelDepositsItem);
        if (levelDepositsCollectionIdentifiers.includes(levelDepositsIdentifier)) {
          return false;
        }
        levelDepositsCollectionIdentifiers.push(levelDepositsIdentifier);
        return true;
      });
      return [...levelDepositsToAdd, ...levelDepositsCollection];
    }
    return levelDepositsCollection;
  }

  protected convertDateFromClient<T extends ILevelDeposits | NewLevelDeposits | PartialUpdateLevelDeposits>(levelDeposits: T): RestOf<T> {
    return {
      ...levelDeposits,
      updateTime: levelDeposits.updateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLevelDeposits: RestLevelDeposits): ILevelDeposits {
    return {
      ...restLevelDeposits,
      updateTime: restLevelDeposits.updateTime ? dayjs(restLevelDeposits.updateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLevelDeposits>): HttpResponse<ILevelDeposits> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLevelDeposits[]>): HttpResponse<ILevelDeposits[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
