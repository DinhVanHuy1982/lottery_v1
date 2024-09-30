import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../role-functions.test-samples';

import { RoleFunctionsFormService } from './role-functions-form.service';

describe('RoleFunctions Form Service', () => {
  let service: RoleFunctionsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleFunctionsFormService);
  });

  describe('Service methods', () => {
    describe('createRoleFunctionsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRoleFunctionsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            roleCode: expect.any(Object),
            functionCode: expect.any(Object),
          }),
        );
      });

      it('passing IRoleFunctions should create a new form with FormGroup', () => {
        const formGroup = service.createRoleFunctionsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            roleCode: expect.any(Object),
            functionCode: expect.any(Object),
          }),
        );
      });
    });

    describe('getRoleFunctions', () => {
      it('should return NewRoleFunctions for default RoleFunctions initial value', () => {
        const formGroup = service.createRoleFunctionsFormGroup(sampleWithNewData);

        const roleFunctions = service.getRoleFunctions(formGroup) as any;

        expect(roleFunctions).toMatchObject(sampleWithNewData);
      });

      it('should return NewRoleFunctions for empty RoleFunctions initial value', () => {
        const formGroup = service.createRoleFunctionsFormGroup();

        const roleFunctions = service.getRoleFunctions(formGroup) as any;

        expect(roleFunctions).toMatchObject({});
      });

      it('should return IRoleFunctions', () => {
        const formGroup = service.createRoleFunctionsFormGroup(sampleWithRequiredData);

        const roleFunctions = service.getRoleFunctions(formGroup) as any;

        expect(roleFunctions).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRoleFunctions should not enable id FormControl', () => {
        const formGroup = service.createRoleFunctionsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRoleFunctions should disable id FormControl', () => {
        const formGroup = service.createRoleFunctionsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
