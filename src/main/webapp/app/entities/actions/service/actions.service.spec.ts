import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IActions } from '../actions.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../actions.test-samples';

import { ActionsService } from './actions.service';

const requireRestSample: IActions = {
  ...sampleWithRequiredData,
};

describe('Actions Service', () => {
  let service: ActionsService;
  let httpMock: HttpTestingController;
  let expectedResult: IActions | IActions[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ActionsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Actions', () => {
      const actions = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(actions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Actions', () => {
      const actions = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(actions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Actions', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Actions', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Actions', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addActionsToCollectionIfMissing', () => {
      it('should add a Actions to an empty array', () => {
        const actions: IActions = sampleWithRequiredData;
        expectedResult = service.addActionsToCollectionIfMissing([], actions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(actions);
      });

      it('should not add a Actions to an array that contains it', () => {
        const actions: IActions = sampleWithRequiredData;
        const actionsCollection: IActions[] = [
          {
            ...actions,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, actions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Actions to an array that doesn't contain it", () => {
        const actions: IActions = sampleWithRequiredData;
        const actionsCollection: IActions[] = [sampleWithPartialData];
        expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, actions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(actions);
      });

      it('should add only unique Actions to an array', () => {
        const actionsArray: IActions[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const actionsCollection: IActions[] = [sampleWithRequiredData];
        expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, ...actionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const actions: IActions = sampleWithRequiredData;
        const actions2: IActions = sampleWithPartialData;
        expectedResult = service.addActionsToCollectionIfMissing([], actions, actions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(actions);
        expect(expectedResult).toContain(actions2);
      });

      it('should accept null and undefined values', () => {
        const actions: IActions = sampleWithRequiredData;
        expectedResult = service.addActionsToCollectionIfMissing([], null, actions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(actions);
      });

      it('should return initial array if no Actions is added', () => {
        const actionsCollection: IActions[] = [sampleWithRequiredData];
        expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, undefined, null);
        expect(expectedResult).toEqual(actionsCollection);
      });
    });

    describe('compareActions', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareActions(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareActions(entity1, entity2);
        const compareResult2 = service.compareActions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareActions(entity1, entity2);
        const compareResult2 = service.compareActions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareActions(entity1, entity2);
        const compareResult2 = service.compareActions(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
