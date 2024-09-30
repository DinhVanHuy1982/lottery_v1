import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../results-every-day.test-samples';

import { ResultsEveryDayFormService } from './results-every-day-form.service';

describe('ResultsEveryDay Form Service', () => {
  let service: ResultsEveryDayFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResultsEveryDayFormService);
  });

  describe('Service methods', () => {
    describe('createResultsEveryDayFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResultsEveryDayFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            resultDate: expect.any(Object),
            prizeCode: expect.any(Object),
            result: expect.any(Object),
          }),
        );
      });

      it('passing IResultsEveryDay should create a new form with FormGroup', () => {
        const formGroup = service.createResultsEveryDayFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            resultDate: expect.any(Object),
            prizeCode: expect.any(Object),
            result: expect.any(Object),
          }),
        );
      });
    });

    describe('getResultsEveryDay', () => {
      it('should return NewResultsEveryDay for default ResultsEveryDay initial value', () => {
        const formGroup = service.createResultsEveryDayFormGroup(sampleWithNewData);

        const resultsEveryDay = service.getResultsEveryDay(formGroup) as any;

        expect(resultsEveryDay).toMatchObject(sampleWithNewData);
      });

      it('should return NewResultsEveryDay for empty ResultsEveryDay initial value', () => {
        const formGroup = service.createResultsEveryDayFormGroup();

        const resultsEveryDay = service.getResultsEveryDay(formGroup) as any;

        expect(resultsEveryDay).toMatchObject({});
      });

      it('should return IResultsEveryDay', () => {
        const formGroup = service.createResultsEveryDayFormGroup(sampleWithRequiredData);

        const resultsEveryDay = service.getResultsEveryDay(formGroup) as any;

        expect(resultsEveryDay).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResultsEveryDay should not enable id FormControl', () => {
        const formGroup = service.createResultsEveryDayFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResultsEveryDay should disable id FormControl', () => {
        const formGroup = service.createResultsEveryDayFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
