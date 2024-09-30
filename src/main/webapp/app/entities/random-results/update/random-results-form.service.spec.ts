import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../random-results.test-samples';

import { RandomResultsFormService } from './random-results-form.service';

describe('RandomResults Form Service', () => {
  let service: RandomResultsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RandomResultsFormService);
  });

  describe('Service methods', () => {
    describe('createRandomResultsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRandomResultsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            randomDate: expect.any(Object),
            prizeCode: expect.any(Object),
            result: expect.any(Object),
            randomUserPlay: expect.any(Object),
            userPlay: expect.any(Object),
            randomUserSuccess: expect.any(Object),
            userSuccess: expect.any(Object),
          }),
        );
      });

      it('passing IRandomResults should create a new form with FormGroup', () => {
        const formGroup = service.createRandomResultsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            randomDate: expect.any(Object),
            prizeCode: expect.any(Object),
            result: expect.any(Object),
            randomUserPlay: expect.any(Object),
            userPlay: expect.any(Object),
            randomUserSuccess: expect.any(Object),
            userSuccess: expect.any(Object),
          }),
        );
      });
    });

    describe('getRandomResults', () => {
      it('should return NewRandomResults for default RandomResults initial value', () => {
        const formGroup = service.createRandomResultsFormGroup(sampleWithNewData);

        const randomResults = service.getRandomResults(formGroup) as any;

        expect(randomResults).toMatchObject(sampleWithNewData);
      });

      it('should return NewRandomResults for empty RandomResults initial value', () => {
        const formGroup = service.createRandomResultsFormGroup();

        const randomResults = service.getRandomResults(formGroup) as any;

        expect(randomResults).toMatchObject({});
      });

      it('should return IRandomResults', () => {
        const formGroup = service.createRandomResultsFormGroup(sampleWithRequiredData);

        const randomResults = service.getRandomResults(formGroup) as any;

        expect(randomResults).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRandomResults should not enable id FormControl', () => {
        const formGroup = service.createRandomResultsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRandomResults should disable id FormControl', () => {
        const formGroup = service.createRandomResultsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
