import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILevelDepositsResult } from '../level-deposits-result.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../level-deposits-result.test-samples';

import { LevelDepositsResultService, RestLevelDepositsResult } from './level-deposits-result.service';

const requireRestSample: RestLevelDepositsResult = {
  ...sampleWithRequiredData,
  resultDate: sampleWithRequiredData.resultDate?.toJSON(),
};

describe('LevelDepositsResult Service', () => {
  let service: LevelDepositsResultService;
  let httpMock: HttpTestingController;
  let expectedResult: ILevelDepositsResult | ILevelDepositsResult[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LevelDepositsResultService);
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

    it('should create a LevelDepositsResult', () => {
      const levelDepositsResult = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(levelDepositsResult).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LevelDepositsResult', () => {
      const levelDepositsResult = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(levelDepositsResult).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LevelDepositsResult', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LevelDepositsResult', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LevelDepositsResult', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLevelDepositsResultToCollectionIfMissing', () => {
      it('should add a LevelDepositsResult to an empty array', () => {
        const levelDepositsResult: ILevelDepositsResult = sampleWithRequiredData;
        expectedResult = service.addLevelDepositsResultToCollectionIfMissing([], levelDepositsResult);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(levelDepositsResult);
      });

      it('should not add a LevelDepositsResult to an array that contains it', () => {
        const levelDepositsResult: ILevelDepositsResult = sampleWithRequiredData;
        const levelDepositsResultCollection: ILevelDepositsResult[] = [
          {
            ...levelDepositsResult,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLevelDepositsResultToCollectionIfMissing(levelDepositsResultCollection, levelDepositsResult);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LevelDepositsResult to an array that doesn't contain it", () => {
        const levelDepositsResult: ILevelDepositsResult = sampleWithRequiredData;
        const levelDepositsResultCollection: ILevelDepositsResult[] = [sampleWithPartialData];
        expectedResult = service.addLevelDepositsResultToCollectionIfMissing(levelDepositsResultCollection, levelDepositsResult);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(levelDepositsResult);
      });

      it('should add only unique LevelDepositsResult to an array', () => {
        const levelDepositsResultArray: ILevelDepositsResult[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const levelDepositsResultCollection: ILevelDepositsResult[] = [sampleWithRequiredData];
        expectedResult = service.addLevelDepositsResultToCollectionIfMissing(levelDepositsResultCollection, ...levelDepositsResultArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const levelDepositsResult: ILevelDepositsResult = sampleWithRequiredData;
        const levelDepositsResult2: ILevelDepositsResult = sampleWithPartialData;
        expectedResult = service.addLevelDepositsResultToCollectionIfMissing([], levelDepositsResult, levelDepositsResult2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(levelDepositsResult);
        expect(expectedResult).toContain(levelDepositsResult2);
      });

      it('should accept null and undefined values', () => {
        const levelDepositsResult: ILevelDepositsResult = sampleWithRequiredData;
        expectedResult = service.addLevelDepositsResultToCollectionIfMissing([], null, levelDepositsResult, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(levelDepositsResult);
      });

      it('should return initial array if no LevelDepositsResult is added', () => {
        const levelDepositsResultCollection: ILevelDepositsResult[] = [sampleWithRequiredData];
        expectedResult = service.addLevelDepositsResultToCollectionIfMissing(levelDepositsResultCollection, undefined, null);
        expect(expectedResult).toEqual(levelDepositsResultCollection);
      });
    });

    describe('compareLevelDepositsResult', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLevelDepositsResult(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLevelDepositsResult(entity1, entity2);
        const compareResult2 = service.compareLevelDepositsResult(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLevelDepositsResult(entity1, entity2);
        const compareResult2 = service.compareLevelDepositsResult(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLevelDepositsResult(entity1, entity2);
        const compareResult2 = service.compareLevelDepositsResult(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
