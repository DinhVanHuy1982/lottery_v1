import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRoles, NewRoles } from '../roles.model';

export type PartialUpdateRoles = Partial<IRoles> & Pick<IRoles, 'id'>;

type RestOf<T extends IRoles | NewRoles> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

export type RestRoles = RestOf<IRoles>;

export type NewRestRoles = RestOf<NewRoles>;

export type PartialUpdateRestRoles = RestOf<PartialUpdateRoles>;

export type EntityResponseType = HttpResponse<IRoles>;
export type EntityArrayResponseType = HttpResponse<IRoles[]>;

@Injectable({ providedIn: 'root' })
export class RolesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/roles');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(roles: NewRoles): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roles);
    return this.http.post<RestRoles>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(roles: IRoles): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roles);
    return this.http
      .put<RestRoles>(`${this.resourceUrl}/${this.getRolesIdentifier(roles)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(roles: PartialUpdateRoles): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roles);
    return this.http
      .patch<RestRoles>(`${this.resourceUrl}/${this.getRolesIdentifier(roles)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRoles>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRoles[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRolesIdentifier(roles: Pick<IRoles, 'id'>): number {
    return roles.id;
  }

  compareRoles(o1: Pick<IRoles, 'id'> | null, o2: Pick<IRoles, 'id'> | null): boolean {
    return o1 && o2 ? this.getRolesIdentifier(o1) === this.getRolesIdentifier(o2) : o1 === o2;
  }

  addRolesToCollectionIfMissing<Type extends Pick<IRoles, 'id'>>(
    rolesCollection: Type[],
    ...rolesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const roles: Type[] = rolesToCheck.filter(isPresent);
    if (roles.length > 0) {
      const rolesCollectionIdentifiers = rolesCollection.map(rolesItem => this.getRolesIdentifier(rolesItem)!);
      const rolesToAdd = roles.filter(rolesItem => {
        const rolesIdentifier = this.getRolesIdentifier(rolesItem);
        if (rolesCollectionIdentifiers.includes(rolesIdentifier)) {
          return false;
        }
        rolesCollectionIdentifiers.push(rolesIdentifier);
        return true;
      });
      return [...rolesToAdd, ...rolesCollection];
    }
    return rolesCollection;
  }

  protected convertDateFromClient<T extends IRoles | NewRoles | PartialUpdateRoles>(roles: T): RestOf<T> {
    return {
      ...roles,
      createTime: roles.createTime?.toJSON() ?? null,
      updateTime: roles.updateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRoles: RestRoles): IRoles {
    return {
      ...restRoles,
      createTime: restRoles.createTime ? dayjs(restRoles.createTime) : undefined,
      updateTime: restRoles.updateTime ? dayjs(restRoles.updateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRoles>): HttpResponse<IRoles> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRoles[]>): HttpResponse<IRoles[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
