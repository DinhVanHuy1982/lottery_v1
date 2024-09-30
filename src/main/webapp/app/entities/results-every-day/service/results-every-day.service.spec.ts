import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResultsEveryDay } from '../results-every-day.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../results-every-day.test-samples';

import { ResultsEveryDayService, RestResultsEveryDay } from './results-every-day.service';

const requireRestSample: RestResultsEveryDay = {
  ...sampleWithRequiredData,
  resultDate: sampleWithRequiredData.resultDate?.toJSON(),
};

describe('ResultsEveryDay Service', () => {
  let service: ResultsEveryDayService;
  let httpMock: HttpTestingController;
  let expectedResult: IResultsEveryDay | IResultsEveryDay[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResultsEveryDayService);
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

    it('should create a ResultsEveryDay', () => {
      const resultsEveryDay = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(resultsEveryDay).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResultsEveryDay', () => {
      const resultsEveryDay = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(resultsEveryDay).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResultsEveryDay', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResultsEveryDay', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResultsEveryDay', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResultsEveryDayToCollectionIfMissing', () => {
      it('should add a ResultsEveryDay to an empty array', () => {
        const resultsEveryDay: IResultsEveryDay = sampleWithRequiredData;
        expectedResult = service.addResultsEveryDayToCollectionIfMissing([], resultsEveryDay);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resultsEveryDay);
      });

      it('should not add a ResultsEveryDay to an array that contains it', () => {
        const resultsEveryDay: IResultsEveryDay = sampleWithRequiredData;
        const resultsEveryDayCollection: IResultsEveryDay[] = [
          {
            ...resultsEveryDay,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResultsEveryDayToCollectionIfMissing(resultsEveryDayCollection, resultsEveryDay);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResultsEveryDay to an array that doesn't contain it", () => {
        const resultsEveryDay: IResultsEveryDay = sampleWithRequiredData;
        const resultsEveryDayCollection: IResultsEveryDay[] = [sampleWithPartialData];
        expectedResult = service.addResultsEveryDayToCollectionIfMissing(resultsEveryDayCollection, resultsEveryDay);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resultsEveryDay);
      });

      it('should add only unique ResultsEveryDay to an array', () => {
        const resultsEveryDayArray: IResultsEveryDay[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const resultsEveryDayCollection: IResultsEveryDay[] = [sampleWithRequiredData];
        expectedResult = service.addResultsEveryDayToCollectionIfMissing(resultsEveryDayCollection, ...resultsEveryDayArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resultsEveryDay: IResultsEveryDay = sampleWithRequiredData;
        const resultsEveryDay2: IResultsEveryDay = sampleWithPartialData;
        expectedResult = service.addResultsEveryDayToCollectionIfMissing([], resultsEveryDay, resultsEveryDay2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resultsEveryDay);
        expect(expectedResult).toContain(resultsEveryDay2);
      });

      it('should accept null and undefined values', () => {
        const resultsEveryDay: IResultsEveryDay = sampleWithRequiredData;
        expectedResult = service.addResultsEveryDayToCollectionIfMissing([], null, resultsEveryDay, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resultsEveryDay);
      });

      it('should return initial array if no ResultsEveryDay is added', () => {
        const resultsEveryDayCollection: IResultsEveryDay[] = [sampleWithRequiredData];
        expectedResult = service.addResultsEveryDayToCollectionIfMissing(resultsEveryDayCollection, undefined, null);
        expect(expectedResult).toEqual(resultsEveryDayCollection);
      });
    });

    describe('compareResultsEveryDay', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResultsEveryDay(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResultsEveryDay(entity1, entity2);
        const compareResult2 = service.compareResultsEveryDay(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResultsEveryDay(entity1, entity2);
        const compareResult2 = service.compareResultsEveryDay(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResultsEveryDay(entity1, entity2);
        const compareResult2 = service.compareResultsEveryDay(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
