import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIntroduceArticleGroup } from '../introduce-article-group.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../introduce-article-group.test-samples';

import { IntroduceArticleGroupService } from './introduce-article-group.service';

const requireRestSample: IIntroduceArticleGroup = {
  ...sampleWithRequiredData,
};

describe('IntroduceArticleGroup Service', () => {
  let service: IntroduceArticleGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IIntroduceArticleGroup | IIntroduceArticleGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IntroduceArticleGroupService);
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

    it('should create a IntroduceArticleGroup', () => {
      const introduceArticleGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(introduceArticleGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IntroduceArticleGroup', () => {
      const introduceArticleGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(introduceArticleGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IntroduceArticleGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IntroduceArticleGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IntroduceArticleGroup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIntroduceArticleGroupToCollectionIfMissing', () => {
      it('should add a IntroduceArticleGroup to an empty array', () => {
        const introduceArticleGroup: IIntroduceArticleGroup = sampleWithRequiredData;
        expectedResult = service.addIntroduceArticleGroupToCollectionIfMissing([], introduceArticleGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(introduceArticleGroup);
      });

      it('should not add a IntroduceArticleGroup to an array that contains it', () => {
        const introduceArticleGroup: IIntroduceArticleGroup = sampleWithRequiredData;
        const introduceArticleGroupCollection: IIntroduceArticleGroup[] = [
          {
            ...introduceArticleGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIntroduceArticleGroupToCollectionIfMissing(introduceArticleGroupCollection, introduceArticleGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IntroduceArticleGroup to an array that doesn't contain it", () => {
        const introduceArticleGroup: IIntroduceArticleGroup = sampleWithRequiredData;
        const introduceArticleGroupCollection: IIntroduceArticleGroup[] = [sampleWithPartialData];
        expectedResult = service.addIntroduceArticleGroupToCollectionIfMissing(introduceArticleGroupCollection, introduceArticleGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(introduceArticleGroup);
      });

      it('should add only unique IntroduceArticleGroup to an array', () => {
        const introduceArticleGroupArray: IIntroduceArticleGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const introduceArticleGroupCollection: IIntroduceArticleGroup[] = [sampleWithRequiredData];
        expectedResult = service.addIntroduceArticleGroupToCollectionIfMissing(
          introduceArticleGroupCollection,
          ...introduceArticleGroupArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const introduceArticleGroup: IIntroduceArticleGroup = sampleWithRequiredData;
        const introduceArticleGroup2: IIntroduceArticleGroup = sampleWithPartialData;
        expectedResult = service.addIntroduceArticleGroupToCollectionIfMissing([], introduceArticleGroup, introduceArticleGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(introduceArticleGroup);
        expect(expectedResult).toContain(introduceArticleGroup2);
      });

      it('should accept null and undefined values', () => {
        const introduceArticleGroup: IIntroduceArticleGroup = sampleWithRequiredData;
        expectedResult = service.addIntroduceArticleGroupToCollectionIfMissing([], null, introduceArticleGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(introduceArticleGroup);
      });

      it('should return initial array if no IntroduceArticleGroup is added', () => {
        const introduceArticleGroupCollection: IIntroduceArticleGroup[] = [sampleWithRequiredData];
        expectedResult = service.addIntroduceArticleGroupToCollectionIfMissing(introduceArticleGroupCollection, undefined, null);
        expect(expectedResult).toEqual(introduceArticleGroupCollection);
      });
    });

    describe('compareIntroduceArticleGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIntroduceArticleGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIntroduceArticleGroup(entity1, entity2);
        const compareResult2 = service.compareIntroduceArticleGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIntroduceArticleGroup(entity1, entity2);
        const compareResult2 = service.compareIntroduceArticleGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIntroduceArticleGroup(entity1, entity2);
        const compareResult2 = service.compareIntroduceArticleGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
