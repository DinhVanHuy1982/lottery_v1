import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeposits, NewDeposits } from '../deposits.model';

export type PartialUpdateDeposits = Partial<IDeposits> & Pick<IDeposits, 'id'>;

type RestOf<T extends IDeposits | NewDeposits> = Omit<T, 'createTime'> & {
  createTime?: string | null;
};

export type RestDeposits = RestOf<IDeposits>;

export type NewRestDeposits = RestOf<NewDeposits>;

export type PartialUpdateRestDeposits = RestOf<PartialUpdateDeposits>;

export type EntityResponseType = HttpResponse<IDeposits>;
export type EntityArrayResponseType = HttpResponse<IDeposits[]>;

@Injectable({ providedIn: 'root' })
export class DepositsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/deposits');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(deposits: NewDeposits): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deposits);
    return this.http
      .post<RestDeposits>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(deposits: IDeposits): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deposits);
    return this.http
      .put<RestDeposits>(`${this.resourceUrl}/${this.getDepositsIdentifier(deposits)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(deposits: PartialUpdateDeposits): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deposits);
    return this.http
      .patch<RestDeposits>(`${this.resourceUrl}/${this.getDepositsIdentifier(deposits)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDeposits>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDeposits[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDepositsIdentifier(deposits: Pick<IDeposits, 'id'>): number {
    return deposits.id;
  }

  compareDeposits(o1: Pick<IDeposits, 'id'> | null, o2: Pick<IDeposits, 'id'> | null): boolean {
    return o1 && o2 ? this.getDepositsIdentifier(o1) === this.getDepositsIdentifier(o2) : o1 === o2;
  }

  addDepositsToCollectionIfMissing<Type extends Pick<IDeposits, 'id'>>(
    depositsCollection: Type[],
    ...depositsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const deposits: Type[] = depositsToCheck.filter(isPresent);
    if (deposits.length > 0) {
      const depositsCollectionIdentifiers = depositsCollection.map(depositsItem => this.getDepositsIdentifier(depositsItem)!);
      const depositsToAdd = deposits.filter(depositsItem => {
        const depositsIdentifier = this.getDepositsIdentifier(depositsItem);
        if (depositsCollectionIdentifiers.includes(depositsIdentifier)) {
          return false;
        }
        depositsCollectionIdentifiers.push(depositsIdentifier);
        return true;
      });
      return [...depositsToAdd, ...depositsCollection];
    }
    return depositsCollection;
  }

  protected convertDateFromClient<T extends IDeposits | NewDeposits | PartialUpdateDeposits>(deposits: T): RestOf<T> {
    return {
      ...deposits,
      createTime: deposits.createTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDeposits: RestDeposits): IDeposits {
    return {
      ...restDeposits,
      createTime: restDeposits.createTime ? dayjs(restDeposits.createTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDeposits>): HttpResponse<IDeposits> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDeposits[]>): HttpResponse<IDeposits[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
