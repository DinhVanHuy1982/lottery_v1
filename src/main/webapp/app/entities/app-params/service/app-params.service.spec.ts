import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAppParams } from '../app-params.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../app-params.test-samples';

import { AppParamsService } from './app-params.service';

const requireRestSample: IAppParams = {
  ...sampleWithRequiredData,
};

describe('AppParams Service', () => {
  let service: AppParamsService;
  let httpMock: HttpTestingController;
  let expectedResult: IAppParams | IAppParams[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AppParamsService);
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

    it('should create a AppParams', () => {
      const appParams = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(appParams).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AppParams', () => {
      const appParams = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(appParams).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AppParams', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AppParams', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AppParams', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAppParamsToCollectionIfMissing', () => {
      it('should add a AppParams to an empty array', () => {
        const appParams: IAppParams = sampleWithRequiredData;
        expectedResult = service.addAppParamsToCollectionIfMissing([], appParams);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appParams);
      });

      it('should not add a AppParams to an array that contains it', () => {
        const appParams: IAppParams = sampleWithRequiredData;
        const appParamsCollection: IAppParams[] = [
          {
            ...appParams,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAppParamsToCollectionIfMissing(appParamsCollection, appParams);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AppParams to an array that doesn't contain it", () => {
        const appParams: IAppParams = sampleWithRequiredData;
        const appParamsCollection: IAppParams[] = [sampleWithPartialData];
        expectedResult = service.addAppParamsToCollectionIfMissing(appParamsCollection, appParams);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appParams);
      });

      it('should add only unique AppParams to an array', () => {
        const appParamsArray: IAppParams[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const appParamsCollection: IAppParams[] = [sampleWithRequiredData];
        expectedResult = service.addAppParamsToCollectionIfMissing(appParamsCollection, ...appParamsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const appParams: IAppParams = sampleWithRequiredData;
        const appParams2: IAppParams = sampleWithPartialData;
        expectedResult = service.addAppParamsToCollectionIfMissing([], appParams, appParams2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appParams);
        expect(expectedResult).toContain(appParams2);
      });

      it('should accept null and undefined values', () => {
        const appParams: IAppParams = sampleWithRequiredData;
        expectedResult = service.addAppParamsToCollectionIfMissing([], null, appParams, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appParams);
      });

      it('should return initial array if no AppParams is added', () => {
        const appParamsCollection: IAppParams[] = [sampleWithRequiredData];
        expectedResult = service.addAppParamsToCollectionIfMissing(appParamsCollection, undefined, null);
        expect(expectedResult).toEqual(appParamsCollection);
      });
    });

    describe('compareAppParams', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAppParams(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAppParams(entity1, entity2);
        const compareResult2 = service.compareAppParams(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAppParams(entity1, entity2);
        const compareResult2 = service.compareAppParams(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAppParams(entity1, entity2);
        const compareResult2 = service.compareAppParams(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
