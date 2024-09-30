import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../introduce-article.test-samples';

import { IntroduceArticleFormService } from './introduce-article-form.service';

describe('IntroduceArticle Form Service', () => {
  let service: IntroduceArticleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IntroduceArticleFormService);
  });

  describe('Service methods', () => {
    describe('createIntroduceArticleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIntroduceArticleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            articleCode: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            fileId: expect.any(Object),
          }),
        );
      });

      it('passing IIntroduceArticle should create a new form with FormGroup', () => {
        const formGroup = service.createIntroduceArticleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            articleCode: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            fileId: expect.any(Object),
          }),
        );
      });
    });

    describe('getIntroduceArticle', () => {
      it('should return NewIntroduceArticle for default IntroduceArticle initial value', () => {
        const formGroup = service.createIntroduceArticleFormGroup(sampleWithNewData);

        const introduceArticle = service.getIntroduceArticle(formGroup) as any;

        expect(introduceArticle).toMatchObject(sampleWithNewData);
      });

      it('should return NewIntroduceArticle for empty IntroduceArticle initial value', () => {
        const formGroup = service.createIntroduceArticleFormGroup();

        const introduceArticle = service.getIntroduceArticle(formGroup) as any;

        expect(introduceArticle).toMatchObject({});
      });

      it('should return IIntroduceArticle', () => {
        const formGroup = service.createIntroduceArticleFormGroup(sampleWithRequiredData);

        const introduceArticle = service.getIntroduceArticle(formGroup) as any;

        expect(introduceArticle).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIntroduceArticle should not enable id FormControl', () => {
        const formGroup = service.createIntroduceArticleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIntroduceArticle should disable id FormControl', () => {
        const formGroup = service.createIntroduceArticleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
