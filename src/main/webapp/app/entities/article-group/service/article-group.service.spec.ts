import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IArticleGroup } from '../article-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../article-group.test-samples';

import { ArticleGroupService, RestArticleGroup } from './article-group.service';

const requireRestSample: RestArticleGroup = {
  ...sampleWithRequiredData,
  createTime: sampleWithRequiredData.createTime?.toJSON(),
  updateTime: sampleWithRequiredData.updateTime?.toJSON(),
};

describe('ArticleGroup Service', () => {
  let service: ArticleGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IArticleGroup | IArticleGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ArticleGroupService);
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

    it('should create a ArticleGroup', () => {
      const articleGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(articleGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ArticleGroup', () => {
      const articleGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(articleGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ArticleGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ArticleGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ArticleGroup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addArticleGroupToCollectionIfMissing', () => {
      it('should add a ArticleGroup to an empty array', () => {
        const articleGroup: IArticleGroup = sampleWithRequiredData;
        expectedResult = service.addArticleGroupToCollectionIfMissing([], articleGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(articleGroup);
      });

      it('should not add a ArticleGroup to an array that contains it', () => {
        const articleGroup: IArticleGroup = sampleWithRequiredData;
        const articleGroupCollection: IArticleGroup[] = [
          {
            ...articleGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addArticleGroupToCollectionIfMissing(articleGroupCollection, articleGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ArticleGroup to an array that doesn't contain it", () => {
        const articleGroup: IArticleGroup = sampleWithRequiredData;
        const articleGroupCollection: IArticleGroup[] = [sampleWithPartialData];
        expectedResult = service.addArticleGroupToCollectionIfMissing(articleGroupCollection, articleGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(articleGroup);
      });

      it('should add only unique ArticleGroup to an array', () => {
        const articleGroupArray: IArticleGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const articleGroupCollection: IArticleGroup[] = [sampleWithRequiredData];
        expectedResult = service.addArticleGroupToCollectionIfMissing(articleGroupCollection, ...articleGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const articleGroup: IArticleGroup = sampleWithRequiredData;
        const articleGroup2: IArticleGroup = sampleWithPartialData;
        expectedResult = service.addArticleGroupToCollectionIfMissing([], articleGroup, articleGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(articleGroup);
        expect(expectedResult).toContain(articleGroup2);
      });

      it('should accept null and undefined values', () => {
        const articleGroup: IArticleGroup = sampleWithRequiredData;
        expectedResult = service.addArticleGroupToCollectionIfMissing([], null, articleGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(articleGroup);
      });

      it('should return initial array if no ArticleGroup is added', () => {
        const articleGroupCollection: IArticleGroup[] = [sampleWithRequiredData];
        expectedResult = service.addArticleGroupToCollectionIfMissing(articleGroupCollection, undefined, null);
        expect(expectedResult).toEqual(articleGroupCollection);
      });
    });

    describe('compareArticleGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareArticleGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareArticleGroup(entity1, entity2);
        const compareResult2 = service.compareArticleGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareArticleGroup(entity1, entity2);
        const compareResult2 = service.compareArticleGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareArticleGroup(entity1, entity2);
        const compareResult2 = service.compareArticleGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
