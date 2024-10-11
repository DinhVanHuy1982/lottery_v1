import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../level-deposits-result.test-samples';

import { LevelDepositsResultFormService } from './level-deposits-result-form.service';

describe('LevelDepositsResult Form Service', () => {
  let service: LevelDepositsResultFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LevelDepositsResultFormService);
  });

  describe('Service methods', () => {
    describe('createLevelDepositsResultFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLevelDepositsResultFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            levelDepositsCode: expect.any(Object),
            randomResultCode: expect.any(Object),
            resultDate: expect.any(Object),
          }),
        );
      });

      it('passing ILevelDepositsResult should create a new form with FormGroup', () => {
        const formGroup = service.createLevelDepositsResultFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            levelDepositsCode: expect.any(Object),
            randomResultCode: expect.any(Object),
            resultDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getLevelDepositsResult', () => {
      it('should return NewLevelDepositsResult for default LevelDepositsResult initial value', () => {
        const formGroup = service.createLevelDepositsResultFormGroup(sampleWithNewData);

        const levelDepositsResult = service.getLevelDepositsResult(formGroup) as any;

        expect(levelDepositsResult).toMatchObject(sampleWithNewData);
      });

      it('should return NewLevelDepositsResult for empty LevelDepositsResult initial value', () => {
        const formGroup = service.createLevelDepositsResultFormGroup();

        const levelDepositsResult = service.getLevelDepositsResult(formGroup) as any;

        expect(levelDepositsResult).toMatchObject({});
      });

      it('should return ILevelDepositsResult', () => {
        const formGroup = service.createLevelDepositsResultFormGroup(sampleWithRequiredData);

        const levelDepositsResult = service.getLevelDepositsResult(formGroup) as any;

        expect(levelDepositsResult).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILevelDepositsResult should not enable id FormControl', () => {
        const formGroup = service.createLevelDepositsResultFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLevelDepositsResult should disable id FormControl', () => {
        const formGroup = service.createLevelDepositsResultFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
