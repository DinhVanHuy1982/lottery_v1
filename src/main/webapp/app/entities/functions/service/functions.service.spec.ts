import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFunctions } from '../functions.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../functions.test-samples';

import { FunctionsService } from './functions.service';

const requireRestSample: IFunctions = {
  ...sampleWithRequiredData,
};

describe('Functions Service', () => {
  let service: FunctionsService;
  let httpMock: HttpTestingController;
  let expectedResult: IFunctions | IFunctions[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FunctionsService);
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

    it('should create a Functions', () => {
      const functions = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(functions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Functions', () => {
      const functions = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(functions).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Functions', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Functions', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Functions', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFunctionsToCollectionIfMissing', () => {
      it('should add a Functions to an empty array', () => {
        const functions: IFunctions = sampleWithRequiredData;
        expectedResult = service.addFunctionsToCollectionIfMissing([], functions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(functions);
      });

      it('should not add a Functions to an array that contains it', () => {
        const functions: IFunctions = sampleWithRequiredData;
        const functionsCollection: IFunctions[] = [
          {
            ...functions,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFunctionsToCollectionIfMissing(functionsCollection, functions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Functions to an array that doesn't contain it", () => {
        const functions: IFunctions = sampleWithRequiredData;
        const functionsCollection: IFunctions[] = [sampleWithPartialData];
        expectedResult = service.addFunctionsToCollectionIfMissing(functionsCollection, functions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(functions);
      });

      it('should add only unique Functions to an array', () => {
        const functionsArray: IFunctions[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const functionsCollection: IFunctions[] = [sampleWithRequiredData];
        expectedResult = service.addFunctionsToCollectionIfMissing(functionsCollection, ...functionsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const functions: IFunctions = sampleWithRequiredData;
        const functions2: IFunctions = sampleWithPartialData;
        expectedResult = service.addFunctionsToCollectionIfMissing([], functions, functions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(functions);
        expect(expectedResult).toContain(functions2);
      });

      it('should accept null and undefined values', () => {
        const functions: IFunctions = sampleWithRequiredData;
        expectedResult = service.addFunctionsToCollectionIfMissing([], null, functions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(functions);
      });

      it('should return initial array if no Functions is added', () => {
        const functionsCollection: IFunctions[] = [sampleWithRequiredData];
        expectedResult = service.addFunctionsToCollectionIfMissing(functionsCollection, undefined, null);
        expect(expectedResult).toEqual(functionsCollection);
      });
    });

    describe('compareFunctions', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFunctions(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFunctions(entity1, entity2);
        const compareResult2 = service.compareFunctions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFunctions(entity1, entity2);
        const compareResult2 = service.compareFunctions(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFunctions(entity1, entity2);
        const compareResult2 = service.compareFunctions(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
