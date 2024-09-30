import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../article-group.test-samples';

import { ArticleGroupFormService } from './article-group-form.service';

describe('ArticleGroup Form Service', () => {
  let service: ArticleGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArticleGroupFormService);
  });

  describe('Service methods', () => {
    describe('createArticleGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArticleGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            title: expect.any(Object),
            mainContent: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
            createName: expect.any(Object),
            updateName: expect.any(Object),
            fileName: expect.any(Object),
            filePath: expect.any(Object),
            fileId: expect.any(Object),
          }),
        );
      });

      it('passing IArticleGroup should create a new form with FormGroup', () => {
        const formGroup = service.createArticleGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            title: expect.any(Object),
            mainContent: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
            createName: expect.any(Object),
            updateName: expect.any(Object),
            fileName: expect.any(Object),
            filePath: expect.any(Object),
            fileId: expect.any(Object),
          }),
        );
      });
    });

    describe('getArticleGroup', () => {
      it('should return NewArticleGroup for default ArticleGroup initial value', () => {
        const formGroup = service.createArticleGroupFormGroup(sampleWithNewData);

        const articleGroup = service.getArticleGroup(formGroup) as any;

        expect(articleGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewArticleGroup for empty ArticleGroup initial value', () => {
        const formGroup = service.createArticleGroupFormGroup();

        const articleGroup = service.getArticleGroup(formGroup) as any;

        expect(articleGroup).toMatchObject({});
      });

      it('should return IArticleGroup', () => {
        const formGroup = service.createArticleGroupFormGroup(sampleWithRequiredData);

        const articleGroup = service.getArticleGroup(formGroup) as any;

        expect(articleGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArticleGroup should not enable id FormControl', () => {
        const formGroup = service.createArticleGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArticleGroup should disable id FormControl', () => {
        const formGroup = service.createArticleGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
