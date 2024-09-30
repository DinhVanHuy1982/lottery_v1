import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRoleFunctionAction } from '../role-function-action.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../role-function-action.test-samples';

import { RoleFunctionActionService } from './role-function-action.service';

const requireRestSample: IRoleFunctionAction = {
  ...sampleWithRequiredData,
};

describe('RoleFunctionAction Service', () => {
  let service: RoleFunctionActionService;
  let httpMock: HttpTestingController;
  let expectedResult: IRoleFunctionAction | IRoleFunctionAction[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RoleFunctionActionService);
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

    it('should create a RoleFunctionAction', () => {
      const roleFunctionAction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(roleFunctionAction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RoleFunctionAction', () => {
      const roleFunctionAction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(roleFunctionAction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RoleFunctionAction', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RoleFunctionAction', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RoleFunctionAction', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRoleFunctionActionToCollectionIfMissing', () => {
      it('should add a RoleFunctionAction to an empty array', () => {
        const roleFunctionAction: IRoleFunctionAction = sampleWithRequiredData;
        expectedResult = service.addRoleFunctionActionToCollectionIfMissing([], roleFunctionAction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleFunctionAction);
      });

      it('should not add a RoleFunctionAction to an array that contains it', () => {
        const roleFunctionAction: IRoleFunctionAction = sampleWithRequiredData;
        const roleFunctionActionCollection: IRoleFunctionAction[] = [
          {
            ...roleFunctionAction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRoleFunctionActionToCollectionIfMissing(roleFunctionActionCollection, roleFunctionAction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RoleFunctionAction to an array that doesn't contain it", () => {
        const roleFunctionAction: IRoleFunctionAction = sampleWithRequiredData;
        const roleFunctionActionCollection: IRoleFunctionAction[] = [sampleWithPartialData];
        expectedResult = service.addRoleFunctionActionToCollectionIfMissing(roleFunctionActionCollection, roleFunctionAction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleFunctionAction);
      });

      it('should add only unique RoleFunctionAction to an array', () => {
        const roleFunctionActionArray: IRoleFunctionAction[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const roleFunctionActionCollection: IRoleFunctionAction[] = [sampleWithRequiredData];
        expectedResult = service.addRoleFunctionActionToCollectionIfMissing(roleFunctionActionCollection, ...roleFunctionActionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const roleFunctionAction: IRoleFunctionAction = sampleWithRequiredData;
        const roleFunctionAction2: IRoleFunctionAction = sampleWithPartialData;
        expectedResult = service.addRoleFunctionActionToCollectionIfMissing([], roleFunctionAction, roleFunctionAction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleFunctionAction);
        expect(expectedResult).toContain(roleFunctionAction2);
      });

      it('should accept null and undefined values', () => {
        const roleFunctionAction: IRoleFunctionAction = sampleWithRequiredData;
        expectedResult = service.addRoleFunctionActionToCollectionIfMissing([], null, roleFunctionAction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleFunctionAction);
      });

      it('should return initial array if no RoleFunctionAction is added', () => {
        const roleFunctionActionCollection: IRoleFunctionAction[] = [sampleWithRequiredData];
        expectedResult = service.addRoleFunctionActionToCollectionIfMissing(roleFunctionActionCollection, undefined, null);
        expect(expectedResult).toEqual(roleFunctionActionCollection);
      });
    });

    describe('compareRoleFunctionAction', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRoleFunctionAction(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRoleFunctionAction(entity1, entity2);
        const compareResult2 = service.compareRoleFunctionAction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRoleFunctionAction(entity1, entity2);
        const compareResult2 = service.compareRoleFunctionAction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRoleFunctionAction(entity1, entity2);
        const compareResult2 = service.compareRoleFunctionAction(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
