import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../app-params.test-samples';

import { AppParamsFormService } from './app-params-form.service';

describe('AppParams Form Service', () => {
  let service: AppParamsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppParamsFormService);
  });

  describe('Service methods', () => {
    describe('createAppParamsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAppParamsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            value: expect.any(Object),
            type: expect.any(Object),
          }),
        );
      });

      it('passing IAppParams should create a new form with FormGroup', () => {
        const formGroup = service.createAppParamsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            value: expect.any(Object),
            type: expect.any(Object),
          }),
        );
      });
    });

    describe('getAppParams', () => {
      it('should return NewAppParams for default AppParams initial value', () => {
        const formGroup = service.createAppParamsFormGroup(sampleWithNewData);

        const appParams = service.getAppParams(formGroup) as any;

        expect(appParams).toMatchObject(sampleWithNewData);
      });

      it('should return NewAppParams for empty AppParams initial value', () => {
        const formGroup = service.createAppParamsFormGroup();

        const appParams = service.getAppParams(formGroup) as any;

        expect(appParams).toMatchObject({});
      });

      it('should return IAppParams', () => {
        const formGroup = service.createAppParamsFormGroup(sampleWithRequiredData);

        const appParams = service.getAppParams(formGroup) as any;

        expect(appParams).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAppParams should not enable id FormControl', () => {
        const formGroup = service.createAppParamsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAppParams should disable id FormControl', () => {
        const formGroup = service.createAppParamsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
