import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFileSaves } from '../file-saves.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../file-saves.test-samples';

import { FileSavesService } from './file-saves.service';

const requireRestSample: IFileSaves = {
  ...sampleWithRequiredData,
};

describe('FileSaves Service', () => {
  let service: FileSavesService;
  let httpMock: HttpTestingController;
  let expectedResult: IFileSaves | IFileSaves[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FileSavesService);
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

    it('should create a FileSaves', () => {
      const fileSaves = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fileSaves).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FileSaves', () => {
      const fileSaves = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fileSaves).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FileSaves', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FileSaves', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FileSaves', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFileSavesToCollectionIfMissing', () => {
      it('should add a FileSaves to an empty array', () => {
        const fileSaves: IFileSaves = sampleWithRequiredData;
        expectedResult = service.addFileSavesToCollectionIfMissing([], fileSaves);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fileSaves);
      });

      it('should not add a FileSaves to an array that contains it', () => {
        const fileSaves: IFileSaves = sampleWithRequiredData;
        const fileSavesCollection: IFileSaves[] = [
          {
            ...fileSaves,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFileSavesToCollectionIfMissing(fileSavesCollection, fileSaves);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FileSaves to an array that doesn't contain it", () => {
        const fileSaves: IFileSaves = sampleWithRequiredData;
        const fileSavesCollection: IFileSaves[] = [sampleWithPartialData];
        expectedResult = service.addFileSavesToCollectionIfMissing(fileSavesCollection, fileSaves);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fileSaves);
      });

      it('should add only unique FileSaves to an array', () => {
        const fileSavesArray: IFileSaves[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fileSavesCollection: IFileSaves[] = [sampleWithRequiredData];
        expectedResult = service.addFileSavesToCollectionIfMissing(fileSavesCollection, ...fileSavesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fileSaves: IFileSaves = sampleWithRequiredData;
        const fileSaves2: IFileSaves = sampleWithPartialData;
        expectedResult = service.addFileSavesToCollectionIfMissing([], fileSaves, fileSaves2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fileSaves);
        expect(expectedResult).toContain(fileSaves2);
      });

      it('should accept null and undefined values', () => {
        const fileSaves: IFileSaves = sampleWithRequiredData;
        expectedResult = service.addFileSavesToCollectionIfMissing([], null, fileSaves, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fileSaves);
      });

      it('should return initial array if no FileSaves is added', () => {
        const fileSavesCollection: IFileSaves[] = [sampleWithRequiredData];
        expectedResult = service.addFileSavesToCollectionIfMissing(fileSavesCollection, undefined, null);
        expect(expectedResult).toEqual(fileSavesCollection);
      });
    });

    describe('compareFileSaves', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFileSaves(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFileSaves(entity1, entity2);
        const compareResult2 = service.compareFileSaves(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFileSaves(entity1, entity2);
        const compareResult2 = service.compareFileSaves(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFileSaves(entity1, entity2);
        const compareResult2 = service.compareFileSaves(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
