import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../functions.test-samples';

import { FunctionsFormService } from './functions-form.service';

describe('Functions Form Service', () => {
  let service: FunctionsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FunctionsFormService);
  });

  describe('Service methods', () => {
    describe('createFunctionsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFunctionsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          }),
        );
      });

      it('passing IFunctions should create a new form with FormGroup', () => {
        const formGroup = service.createFunctionsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          }),
        );
      });
    });

    describe('getFunctions', () => {
      it('should return NewFunctions for default Functions initial value', () => {
        const formGroup = service.createFunctionsFormGroup(sampleWithNewData);

        const functions = service.getFunctions(formGroup) as any;

        expect(functions).toMatchObject(sampleWithNewData);
      });

      it('should return NewFunctions for empty Functions initial value', () => {
        const formGroup = service.createFunctionsFormGroup();

        const functions = service.getFunctions(formGroup) as any;

        expect(functions).toMatchObject({});
      });

      it('should return IFunctions', () => {
        const formGroup = service.createFunctionsFormGroup(sampleWithRequiredData);

        const functions = service.getFunctions(formGroup) as any;

        expect(functions).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFunctions should not enable id FormControl', () => {
        const formGroup = service.createFunctionsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFunctions should disable id FormControl', () => {
        const formGroup = service.createFunctionsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
