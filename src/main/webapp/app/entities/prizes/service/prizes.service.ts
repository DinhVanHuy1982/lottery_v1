import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrizes, NewPrizes } from '../prizes.model';

export type PartialUpdatePrizes = Partial<IPrizes> & Pick<IPrizes, 'id'>;

type RestOf<T extends IPrizes | NewPrizes> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

export type RestPrizes = RestOf<IPrizes>;

export type NewRestPrizes = RestOf<NewPrizes>;

export type PartialUpdateRestPrizes = RestOf<PartialUpdatePrizes>;

export type EntityResponseType = HttpResponse<IPrizes>;
export type EntityArrayResponseType = HttpResponse<IPrizes[]>;

@Injectable({ providedIn: 'root' })
export class PrizesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prizes');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(prizes: NewPrizes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prizes);
    return this.http
      .post<RestPrizes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(prizes: IPrizes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prizes);
    return this.http
      .put<RestPrizes>(`${this.resourceUrl}/${this.getPrizesIdentifier(prizes)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(prizes: PartialUpdatePrizes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prizes);
    return this.http
      .patch<RestPrizes>(`${this.resourceUrl}/${this.getPrizesIdentifier(prizes)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPrizes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPrizes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrizesIdentifier(prizes: Pick<IPrizes, 'id'>): number {
    return prizes.id;
  }

  comparePrizes(o1: Pick<IPrizes, 'id'> | null, o2: Pick<IPrizes, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrizesIdentifier(o1) === this.getPrizesIdentifier(o2) : o1 === o2;
  }

  addPrizesToCollectionIfMissing<Type extends Pick<IPrizes, 'id'>>(
    prizesCollection: Type[],
    ...prizesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prizes: Type[] = prizesToCheck.filter(isPresent);
    if (prizes.length > 0) {
      const prizesCollectionIdentifiers = prizesCollection.map(prizesItem => this.getPrizesIdentifier(prizesItem)!);
      const prizesToAdd = prizes.filter(prizesItem => {
        const prizesIdentifier = this.getPrizesIdentifier(prizesItem);
        if (prizesCollectionIdentifiers.includes(prizesIdentifier)) {
          return false;
        }
        prizesCollectionIdentifiers.push(prizesIdentifier);
        return true;
      });
      return [...prizesToAdd, ...prizesCollection];
    }
    return prizesCollection;
  }

  protected convertDateFromClient<T extends IPrizes | NewPrizes | PartialUpdatePrizes>(prizes: T): RestOf<T> {
    return {
      ...prizes,
      createTime: prizes.createTime?.toJSON() ?? null,
      updateTime: prizes.updateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPrizes: RestPrizes): IPrizes {
    return {
      ...restPrizes,
      createTime: restPrizes.createTime ? dayjs(restPrizes.createTime) : undefined,
      updateTime: restPrizes.updateTime ? dayjs(restPrizes.updateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPrizes>): HttpResponse<IPrizes> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPrizes[]>): HttpResponse<IPrizes[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
