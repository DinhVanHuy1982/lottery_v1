import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../deposits.test-samples';

import { DepositsFormService } from './deposits-form.service';

describe('Deposits Form Service', () => {
  let service: DepositsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepositsFormService);
  });

  describe('Service methods', () => {
    describe('createDepositsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDepositsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            articleCode: expect.any(Object),
            netwrokCard: expect.any(Object),
            valueCard: expect.any(Object),
            createTime: expect.any(Object),
            seriCard: expect.any(Object),
            codeCard: expect.any(Object),
            status: expect.any(Object),
            userAppose: expect.any(Object),
            valueChoice: expect.any(Object),
            phoneNumber: expect.any(Object),
          }),
        );
      });

      it('passing IDeposits should create a new form with FormGroup', () => {
        const formGroup = service.createDepositsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            articleCode: expect.any(Object),
            netwrokCard: expect.any(Object),
            valueCard: expect.any(Object),
            createTime: expect.any(Object),
            seriCard: expect.any(Object),
            codeCard: expect.any(Object),
            status: expect.any(Object),
            userAppose: expect.any(Object),
            valueChoice: expect.any(Object),
            phoneNumber: expect.any(Object),
          }),
        );
      });
    });

    describe('getDeposits', () => {
      it('should return NewDeposits for default Deposits initial value', () => {
        const formGroup = service.createDepositsFormGroup(sampleWithNewData);

        const deposits = service.getDeposits(formGroup) as any;

        expect(deposits).toMatchObject(sampleWithNewData);
      });

      it('should return NewDeposits for empty Deposits initial value', () => {
        const formGroup = service.createDepositsFormGroup();

        const deposits = service.getDeposits(formGroup) as any;

        expect(deposits).toMatchObject({});
      });

      it('should return IDeposits', () => {
        const formGroup = service.createDepositsFormGroup(sampleWithRequiredData);

        const deposits = service.getDeposits(formGroup) as any;

        expect(deposits).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDeposits should not enable id FormControl', () => {
        const formGroup = service.createDepositsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDeposits should disable id FormControl', () => {
        const formGroup = service.createDepositsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
