import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFileSaves, NewFileSaves } from '../file-saves.model';

export type PartialUpdateFileSaves = Partial<IFileSaves> & Pick<IFileSaves, 'id'>;

export type EntityResponseType = HttpResponse<IFileSaves>;
export type EntityArrayResponseType = HttpResponse<IFileSaves[]>;

@Injectable({ providedIn: 'root' })
export class FileSavesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/file-saves');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(fileSaves: NewFileSaves): Observable<EntityResponseType> {
    return this.http.post<IFileSaves>(this.resourceUrl, fileSaves, { observe: 'response' });
  }

  update(fileSaves: IFileSaves): Observable<EntityResponseType> {
    return this.http.put<IFileSaves>(`${this.resourceUrl}/${this.getFileSavesIdentifier(fileSaves)}`, fileSaves, { observe: 'response' });
  }

  partialUpdate(fileSaves: PartialUpdateFileSaves): Observable<EntityResponseType> {
    return this.http.patch<IFileSaves>(`${this.resourceUrl}/${this.getFileSavesIdentifier(fileSaves)}`, fileSaves, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFileSaves>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFileSaves[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFileSavesIdentifier(fileSaves: Pick<IFileSaves, 'id'>): number {
    return fileSaves.id;
  }

  compareFileSaves(o1: Pick<IFileSaves, 'id'> | null, o2: Pick<IFileSaves, 'id'> | null): boolean {
    return o1 && o2 ? this.getFileSavesIdentifier(o1) === this.getFileSavesIdentifier(o2) : o1 === o2;
  }

  addFileSavesToCollectionIfMissing<Type extends Pick<IFileSaves, 'id'>>(
    fileSavesCollection: Type[],
    ...fileSavesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fileSaves: Type[] = fileSavesToCheck.filter(isPresent);
    if (fileSaves.length > 0) {
      const fileSavesCollectionIdentifiers = fileSavesCollection.map(fileSavesItem => this.getFileSavesIdentifier(fileSavesItem)!);
      const fileSavesToAdd = fileSaves.filter(fileSavesItem => {
        const fileSavesIdentifier = this.getFileSavesIdentifier(fileSavesItem);
        if (fileSavesCollectionIdentifiers.includes(fileSavesIdentifier)) {
          return false;
        }
        fileSavesCollectionIdentifiers.push(fileSavesIdentifier);
        return true;
      });
      return [...fileSavesToAdd, ...fileSavesCollection];
    }
    return fileSavesCollection;
  }
}
