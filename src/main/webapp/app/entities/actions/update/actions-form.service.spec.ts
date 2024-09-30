import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../actions.test-samples';

import { ActionsFormService } from './actions-form.service';

describe('Actions Form Service', () => {
  let service: ActionsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActionsFormService);
  });

  describe('Service methods', () => {
    describe('createActionsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createActionsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          }),
        );
      });

      it('passing IActions should create a new form with FormGroup', () => {
        const formGroup = service.createActionsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          }),
        );
      });
    });

    describe('getActions', () => {
      it('should return NewActions for default Actions initial value', () => {
        const formGroup = service.createActionsFormGroup(sampleWithNewData);

        const actions = service.getActions(formGroup) as any;

        expect(actions).toMatchObject(sampleWithNewData);
      });

      it('should return NewActions for empty Actions initial value', () => {
        const formGroup = service.createActionsFormGroup();

        const actions = service.getActions(formGroup) as any;

        expect(actions).toMatchObject({});
      });

      it('should return IActions', () => {
        const formGroup = service.createActionsFormGroup(sampleWithRequiredData);

        const actions = service.getActions(formGroup) as any;

        expect(actions).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IActions should not enable id FormControl', () => {
        const formGroup = service.createActionsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewActions should disable id FormControl', () => {
        const formGroup = service.createActionsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
