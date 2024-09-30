import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../articles.test-samples';

import { ArticlesFormService } from './articles-form.service';

describe('Articles Form Service', () => {
  let service: ArticlesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArticlesFormService);
  });

  describe('Service methods', () => {
    describe('createArticlesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArticlesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            fileId: expect.any(Object),
            updateName: expect.any(Object),
            numberChoice: expect.any(Object),
            numberOfDigits: expect.any(Object),
            timeStart: expect.any(Object),
            timeEnd: expect.any(Object),
          }),
        );
      });

      it('passing IArticles should create a new form with FormGroup', () => {
        const formGroup = service.createArticlesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            fileId: expect.any(Object),
            updateName: expect.any(Object),
            numberChoice: expect.any(Object),
            numberOfDigits: expect.any(Object),
            timeStart: expect.any(Object),
            timeEnd: expect.any(Object),
          }),
        );
      });
    });

    describe('getArticles', () => {
      it('should return NewArticles for default Articles initial value', () => {
        const formGroup = service.createArticlesFormGroup(sampleWithNewData);

        const articles = service.getArticles(formGroup) as any;

        expect(articles).toMatchObject(sampleWithNewData);
      });

      it('should return NewArticles for empty Articles initial value', () => {
        const formGroup = service.createArticlesFormGroup();

        const articles = service.getArticles(formGroup) as any;

        expect(articles).toMatchObject({});
      });

      it('should return IArticles', () => {
        const formGroup = service.createArticlesFormGroup(sampleWithRequiredData);

        const articles = service.getArticles(formGroup) as any;

        expect(articles).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArticles should not enable id FormControl', () => {
        const formGroup = service.createArticlesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArticles should disable id FormControl', () => {
        const formGroup = service.createArticlesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
