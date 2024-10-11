import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../level-deposits.test-samples';

import { LevelDepositsFormService } from './level-deposits-form.service';

describe('LevelDeposits Form Service', () => {
  let service: LevelDepositsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LevelDepositsFormService);
  });

  describe('Service methods', () => {
    describe('createLevelDepositsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLevelDepositsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            minPrice: expect.any(Object),
            updateName: expect.any(Object),
            updateTime: expect.any(Object),
            articleCode: expect.any(Object),
          }),
        );
      });

      it('passing ILevelDeposits should create a new form with FormGroup', () => {
        const formGroup = service.createLevelDepositsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            minPrice: expect.any(Object),
            updateName: expect.any(Object),
            updateTime: expect.any(Object),
            articleCode: expect.any(Object),
          }),
        );
      });
    });

    describe('getLevelDeposits', () => {
      it('should return NewLevelDeposits for default LevelDeposits initial value', () => {
        const formGroup = service.createLevelDepositsFormGroup(sampleWithNewData);

        const levelDeposits = service.getLevelDeposits(formGroup) as any;

        expect(levelDeposits).toMatchObject(sampleWithNewData);
      });

      it('should return NewLevelDeposits for empty LevelDeposits initial value', () => {
        const formGroup = service.createLevelDepositsFormGroup();

        const levelDeposits = service.getLevelDeposits(formGroup) as any;

        expect(levelDeposits).toMatchObject({});
      });

      it('should return ILevelDeposits', () => {
        const formGroup = service.createLevelDepositsFormGroup(sampleWithRequiredData);

        const levelDeposits = service.getLevelDeposits(formGroup) as any;

        expect(levelDeposits).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILevelDeposits should not enable id FormControl', () => {
        const formGroup = service.createLevelDepositsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLevelDeposits should disable id FormControl', () => {
        const formGroup = service.createLevelDepositsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
