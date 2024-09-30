import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IArticles } from '../articles.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../articles.test-samples';

import { ArticlesService } from './articles.service';

const requireRestSample: IArticles = {
  ...sampleWithRequiredData,
};

describe('Articles Service', () => {
  let service: ArticlesService;
  let httpMock: HttpTestingController;
  let expectedResult: IArticles | IArticles[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ArticlesService);
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

    it('should create a Articles', () => {
      const articles = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(articles).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Articles', () => {
      const articles = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(articles).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Articles', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Articles', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Articles', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addArticlesToCollectionIfMissing', () => {
      it('should add a Articles to an empty array', () => {
        const articles: IArticles = sampleWithRequiredData;
        expectedResult = service.addArticlesToCollectionIfMissing([], articles);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(articles);
      });

      it('should not add a Articles to an array that contains it', () => {
        const articles: IArticles = sampleWithRequiredData;
        const articlesCollection: IArticles[] = [
          {
            ...articles,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addArticlesToCollectionIfMissing(articlesCollection, articles);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Articles to an array that doesn't contain it", () => {
        const articles: IArticles = sampleWithRequiredData;
        const articlesCollection: IArticles[] = [sampleWithPartialData];
        expectedResult = service.addArticlesToCollectionIfMissing(articlesCollection, articles);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(articles);
      });

      it('should add only unique Articles to an array', () => {
        const articlesArray: IArticles[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const articlesCollection: IArticles[] = [sampleWithRequiredData];
        expectedResult = service.addArticlesToCollectionIfMissing(articlesCollection, ...articlesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const articles: IArticles = sampleWithRequiredData;
        const articles2: IArticles = sampleWithPartialData;
        expectedResult = service.addArticlesToCollectionIfMissing([], articles, articles2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(articles);
        expect(expectedResult).toContain(articles2);
      });

      it('should accept null and undefined values', () => {
        const articles: IArticles = sampleWithRequiredData;
        expectedResult = service.addArticlesToCollectionIfMissing([], null, articles, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(articles);
      });

      it('should return initial array if no Articles is added', () => {
        const articlesCollection: IArticles[] = [sampleWithRequiredData];
        expectedResult = service.addArticlesToCollectionIfMissing(articlesCollection, undefined, null);
        expect(expectedResult).toEqual(articlesCollection);
      });
    });

    describe('compareArticles', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareArticles(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareArticles(entity1, entity2);
        const compareResult2 = service.compareArticles(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareArticles(entity1, entity2);
        const compareResult2 = service.compareArticles(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareArticles(entity1, entity2);
        const compareResult2 = service.compareArticles(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
