import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrizes } from '../prizes.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../prizes.test-samples';

import { PrizesService, RestPrizes } from './prizes.service';

const requireRestSample: RestPrizes = {
  ...sampleWithRequiredData,
  createTime: sampleWithRequiredData.createTime?.toJSON(),
  updateTime: sampleWithRequiredData.updateTime?.toJSON(),
};

describe('Prizes Service', () => {
  let service: PrizesService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrizes | IPrizes[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrizesService);
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

    it('should create a Prizes', () => {
      const prizes = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(prizes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Prizes', () => {
      const prizes = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(prizes).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Prizes', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Prizes', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Prizes', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrizesToCollectionIfMissing', () => {
      it('should add a Prizes to an empty array', () => {
        const prizes: IPrizes = sampleWithRequiredData;
        expectedResult = service.addPrizesToCollectionIfMissing([], prizes);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prizes);
      });

      it('should not add a Prizes to an array that contains it', () => {
        const prizes: IPrizes = sampleWithRequiredData;
        const prizesCollection: IPrizes[] = [
          {
            ...prizes,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrizesToCollectionIfMissing(prizesCollection, prizes);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Prizes to an array that doesn't contain it", () => {
        const prizes: IPrizes = sampleWithRequiredData;
        const prizesCollection: IPrizes[] = [sampleWithPartialData];
        expectedResult = service.addPrizesToCollectionIfMissing(prizesCollection, prizes);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prizes);
      });

      it('should add only unique Prizes to an array', () => {
        const prizesArray: IPrizes[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const prizesCollection: IPrizes[] = [sampleWithRequiredData];
        expectedResult = service.addPrizesToCollectionIfMissing(prizesCollection, ...prizesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const prizes: IPrizes = sampleWithRequiredData;
        const prizes2: IPrizes = sampleWithPartialData;
        expectedResult = service.addPrizesToCollectionIfMissing([], prizes, prizes2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prizes);
        expect(expectedResult).toContain(prizes2);
      });

      it('should accept null and undefined values', () => {
        const prizes: IPrizes = sampleWithRequiredData;
        expectedResult = service.addPrizesToCollectionIfMissing([], null, prizes, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prizes);
      });

      it('should return initial array if no Prizes is added', () => {
        const prizesCollection: IPrizes[] = [sampleWithRequiredData];
        expectedResult = service.addPrizesToCollectionIfMissing(prizesCollection, undefined, null);
        expect(expectedResult).toEqual(prizesCollection);
      });
    });

    describe('comparePrizes', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrizes(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrizes(entity1, entity2);
        const compareResult2 = service.comparePrizes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrizes(entity1, entity2);
        const compareResult2 = service.comparePrizes(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrizes(entity1, entity2);
        const compareResult2 = service.comparePrizes(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
