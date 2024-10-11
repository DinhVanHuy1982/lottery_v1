import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILevelDeposits } from '../level-deposits.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../level-deposits.test-samples';

import { LevelDepositsService, RestLevelDeposits } from './level-deposits.service';

const requireRestSample: RestLevelDeposits = {
  ...sampleWithRequiredData,
  updateTime: sampleWithRequiredData.updateTime?.toJSON(),
};

describe('LevelDeposits Service', () => {
  let service: LevelDepositsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILevelDeposits | ILevelDeposits[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LevelDepositsService);
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

    it('should create a LevelDeposits', () => {
      const levelDeposits = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(levelDeposits).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LevelDeposits', () => {
      const levelDeposits = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(levelDeposits).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LevelDeposits', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LevelDeposits', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LevelDeposits', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLevelDepositsToCollectionIfMissing', () => {
      it('should add a LevelDeposits to an empty array', () => {
        const levelDeposits: ILevelDeposits = sampleWithRequiredData;
        expectedResult = service.addLevelDepositsToCollectionIfMissing([], levelDeposits);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(levelDeposits);
      });

      it('should not add a LevelDeposits to an array that contains it', () => {
        const levelDeposits: ILevelDeposits = sampleWithRequiredData;
        const levelDepositsCollection: ILevelDeposits[] = [
          {
            ...levelDeposits,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLevelDepositsToCollectionIfMissing(levelDepositsCollection, levelDeposits);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LevelDeposits to an array that doesn't contain it", () => {
        const levelDeposits: ILevelDeposits = sampleWithRequiredData;
        const levelDepositsCollection: ILevelDeposits[] = [sampleWithPartialData];
        expectedResult = service.addLevelDepositsToCollectionIfMissing(levelDepositsCollection, levelDeposits);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(levelDeposits);
      });

      it('should add only unique LevelDeposits to an array', () => {
        const levelDepositsArray: ILevelDeposits[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const levelDepositsCollection: ILevelDeposits[] = [sampleWithRequiredData];
        expectedResult = service.addLevelDepositsToCollectionIfMissing(levelDepositsCollection, ...levelDepositsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const levelDeposits: ILevelDeposits = sampleWithRequiredData;
        const levelDeposits2: ILevelDeposits = sampleWithPartialData;
        expectedResult = service.addLevelDepositsToCollectionIfMissing([], levelDeposits, levelDeposits2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(levelDeposits);
        expect(expectedResult).toContain(levelDeposits2);
      });

      it('should accept null and undefined values', () => {
        const levelDeposits: ILevelDeposits = sampleWithRequiredData;
        expectedResult = service.addLevelDepositsToCollectionIfMissing([], null, levelDeposits, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(levelDeposits);
      });

      it('should return initial array if no LevelDeposits is added', () => {
        const levelDepositsCollection: ILevelDeposits[] = [sampleWithRequiredData];
        expectedResult = service.addLevelDepositsToCollectionIfMissing(levelDepositsCollection, undefined, null);
        expect(expectedResult).toEqual(levelDepositsCollection);
      });
    });

    describe('compareLevelDeposits', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLevelDeposits(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLevelDeposits(entity1, entity2);
        const compareResult2 = service.compareLevelDeposits(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLevelDeposits(entity1, entity2);
        const compareResult2 = service.compareLevelDeposits(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLevelDeposits(entity1, entity2);
        const compareResult2 = service.compareLevelDeposits(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
