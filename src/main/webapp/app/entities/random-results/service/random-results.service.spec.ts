import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRandomResults } from '../random-results.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../random-results.test-samples';

import { RandomResultsService, RestRandomResults } from './random-results.service';

const requireRestSample: RestRandomResults = {
  ...sampleWithRequiredData,
  randomDate: sampleWithRequiredData.randomDate?.toJSON(),
};

describe('RandomResults Service', () => {
  let service: RandomResultsService;
  let httpMock: HttpTestingController;
  let expectedResult: IRandomResults | IRandomResults[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RandomResultsService);
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

    it('should create a RandomResults', () => {
      const randomResults = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(randomResults).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RandomResults', () => {
      const randomResults = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(randomResults).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RandomResults', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RandomResults', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RandomResults', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRandomResultsToCollectionIfMissing', () => {
      it('should add a RandomResults to an empty array', () => {
        const randomResults: IRandomResults = sampleWithRequiredData;
        expectedResult = service.addRandomResultsToCollectionIfMissing([], randomResults);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(randomResults);
      });

      it('should not add a RandomResults to an array that contains it', () => {
        const randomResults: IRandomResults = sampleWithRequiredData;
        const randomResultsCollection: IRandomResults[] = [
          {
            ...randomResults,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRandomResultsToCollectionIfMissing(randomResultsCollection, randomResults);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RandomResults to an array that doesn't contain it", () => {
        const randomResults: IRandomResults = sampleWithRequiredData;
        const randomResultsCollection: IRandomResults[] = [sampleWithPartialData];
        expectedResult = service.addRandomResultsToCollectionIfMissing(randomResultsCollection, randomResults);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(randomResults);
      });

      it('should add only unique RandomResults to an array', () => {
        const randomResultsArray: IRandomResults[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const randomResultsCollection: IRandomResults[] = [sampleWithRequiredData];
        expectedResult = service.addRandomResultsToCollectionIfMissing(randomResultsCollection, ...randomResultsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const randomResults: IRandomResults = sampleWithRequiredData;
        const randomResults2: IRandomResults = sampleWithPartialData;
        expectedResult = service.addRandomResultsToCollectionIfMissing([], randomResults, randomResults2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(randomResults);
        expect(expectedResult).toContain(randomResults2);
      });

      it('should accept null and undefined values', () => {
        const randomResults: IRandomResults = sampleWithRequiredData;
        expectedResult = service.addRandomResultsToCollectionIfMissing([], null, randomResults, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(randomResults);
      });

      it('should return initial array if no RandomResults is added', () => {
        const randomResultsCollection: IRandomResults[] = [sampleWithRequiredData];
        expectedResult = service.addRandomResultsToCollectionIfMissing(randomResultsCollection, undefined, null);
        expect(expectedResult).toEqual(randomResultsCollection);
      });
    });

    describe('compareRandomResults', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRandomResults(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRandomResults(entity1, entity2);
        const compareResult2 = service.compareRandomResults(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRandomResults(entity1, entity2);
        const compareResult2 = service.compareRandomResults(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRandomResults(entity1, entity2);
        const compareResult2 = service.compareRandomResults(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
