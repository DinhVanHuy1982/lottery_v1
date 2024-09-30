import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../introduce-article-group.test-samples';

import { IntroduceArticleGroupFormService } from './introduce-article-group-form.service';

describe('IntroduceArticleGroup Form Service', () => {
  let service: IntroduceArticleGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IntroduceArticleGroupFormService);
  });

  describe('Service methods', () => {
    describe('createIntroduceArticleGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIntroduceArticleGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            articleGroupCode: expect.any(Object),
            fileId: expect.any(Object),
            titleIntroduce: expect.any(Object),
            contentIntroduce: expect.any(Object),
          }),
        );
      });

      it('passing IIntroduceArticleGroup should create a new form with FormGroup', () => {
        const formGroup = service.createIntroduceArticleGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            articleGroupCode: expect.any(Object),
            fileId: expect.any(Object),
            titleIntroduce: expect.any(Object),
            contentIntroduce: expect.any(Object),
          }),
        );
      });
    });

    describe('getIntroduceArticleGroup', () => {
      it('should return NewIntroduceArticleGroup for default IntroduceArticleGroup initial value', () => {
        const formGroup = service.createIntroduceArticleGroupFormGroup(sampleWithNewData);

        const introduceArticleGroup = service.getIntroduceArticleGroup(formGroup) as any;

        expect(introduceArticleGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewIntroduceArticleGroup for empty IntroduceArticleGroup initial value', () => {
        const formGroup = service.createIntroduceArticleGroupFormGroup();

        const introduceArticleGroup = service.getIntroduceArticleGroup(formGroup) as any;

        expect(introduceArticleGroup).toMatchObject({});
      });

      it('should return IIntroduceArticleGroup', () => {
        const formGroup = service.createIntroduceArticleGroupFormGroup(sampleWithRequiredData);

        const introduceArticleGroup = service.getIntroduceArticleGroup(formGroup) as any;

        expect(introduceArticleGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIntroduceArticleGroup should not enable id FormControl', () => {
        const formGroup = service.createIntroduceArticleGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIntroduceArticleGroup should disable id FormControl', () => {
        const formGroup = service.createIntroduceArticleGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
