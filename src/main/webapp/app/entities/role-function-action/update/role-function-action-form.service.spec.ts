import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../role-function-action.test-samples';

import { RoleFunctionActionFormService } from './role-function-action-form.service';

describe('RoleFunctionAction Form Service', () => {
  let service: RoleFunctionActionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleFunctionActionFormService);
  });

  describe('Service methods', () => {
    describe('createRoleFunctionActionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRoleFunctionActionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleFunctionCode: expect.any(Object),
            actionCode: expect.any(Object),
          }),
        );
      });

      it('passing IRoleFunctionAction should create a new form with FormGroup', () => {
        const formGroup = service.createRoleFunctionActionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roleFunctionCode: expect.any(Object),
            actionCode: expect.any(Object),
          }),
        );
      });
    });

    describe('getRoleFunctionAction', () => {
      it('should return NewRoleFunctionAction for default RoleFunctionAction initial value', () => {
        const formGroup = service.createRoleFunctionActionFormGroup(sampleWithNewData);

        const roleFunctionAction = service.getRoleFunctionAction(formGroup) as any;

        expect(roleFunctionAction).toMatchObject(sampleWithNewData);
      });

      it('should return NewRoleFunctionAction for empty RoleFunctionAction initial value', () => {
        const formGroup = service.createRoleFunctionActionFormGroup();

        const roleFunctionAction = service.getRoleFunctionAction(formGroup) as any;

        expect(roleFunctionAction).toMatchObject({});
      });

      it('should return IRoleFunctionAction', () => {
        const formGroup = service.createRoleFunctionActionFormGroup(sampleWithRequiredData);

        const roleFunctionAction = service.getRoleFunctionAction(formGroup) as any;

        expect(roleFunctionAction).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRoleFunctionAction should not enable id FormControl', () => {
        const formGroup = service.createRoleFunctionActionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRoleFunctionAction should disable id FormControl', () => {
        const formGroup = service.createRoleFunctionActionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
