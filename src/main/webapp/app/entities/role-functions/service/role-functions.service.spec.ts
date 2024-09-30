import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRoleFunctions } from '../role-functions.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../role-functions.test-samples';

import { RoleFunctionsService } from './role-functions.service';

const requireRestSample: IRoleFunctions = {
  ...sampleWithRequiredData,
};

describe('RoleFunctions Service', () => {
  let service: RoleFunctionsService;
  let httpMock: HttpTestingController;
  let expectedResult: IRoleFunctions | IRoleFunctions[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RoleFunctionsService);
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

    it('should create a RoleFunctions', () => {
      const roleFunctions = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(roleFunctions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RoleFunctions', () => {
      const roleFunctions = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(roleFunctions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RoleFunctions', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RoleFunctions', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RoleFunctions', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRoleFunctionsToCollectionIfMissing', () => {
      it('should add a RoleFunctions to an empty array', () => {
        const roleFunctions: IRoleFunctions = sampleWithRequiredData;
        expectedResult = service.addRoleFunctionsToCollectionIfMissing([], roleFunctions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleFunctions);
      });

      it('should not add a RoleFunctions to an array that contains it', () => {
        const roleFunctions: IRoleFunctions = sampleWithRequiredData;
        const roleFunctionsCollection: IRoleFunctions[] = [
          {
            ...roleFunctions,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRoleFunctionsToCollectionIfMissing(roleFunctionsCollection, roleFunctions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RoleFunctions to an array that doesn't contain it", () => {
        const roleFunctions: IRoleFunctions = sampleWithRequiredData;
        const roleFunctionsCollection: IRoleFunctions[] = [sampleWithPartialData];
        expectedResult = service.addRoleFunctionsToCollectionIfMissing(roleFunctionsCollection, roleFunctions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleFunctions);
      });

      it('should add only unique RoleFunctions to an array', () => {
        const roleFunctionsArray: IRoleFunctions[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const roleFunctionsCollection: IRoleFunctions[] = [sampleWithRequiredData];
        expectedResult = service.addRoleFunctionsToCollectionIfMissing(roleFunctionsCollection, ...roleFunctionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const roleFunctions: IRoleFunctions = sampleWithRequiredData;
        const roleFunctions2: IRoleFunctions = sampleWithPartialData;
        expectedResult = service.addRoleFunctionsToCollectionIfMissing([], roleFunctions, roleFunctions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleFunctions);
        expect(expectedResult).toContain(roleFunctions2);
      });

      it('should accept null and undefined values', () => {
        const roleFunctions: IRoleFunctions = sampleWithRequiredData;
        expectedResult = service.addRoleFunctionsToCollectionIfMissing([], null, roleFunctions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleFunctions);
      });

      it('should return initial array if no RoleFunctions is added', () => {
        const roleFunctionsCollection: IRoleFunctions[] = [sampleWithRequiredData];
        expectedResult = service.addRoleFunctionsToCollectionIfMissing(roleFunctionsCollection, undefined, null);
        expect(expectedResult).toEqual(roleFunctionsCollection);
      });
    });

    describe('compareRoleFunctions', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRoleFunctions(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRoleFunctions(entity1, entity2);
        const compareResult2 = service.compareRoleFunctions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRoleFunctions(entity1, entity2);
        const compareResult2 = service.compareRoleFunctions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRoleFunctions(entity1, entity2);
        const compareResult2 = service.compareRoleFunctions(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
