import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../prizes.test-samples';

import { PrizesFormService } from './prizes-form.service';

describe('Prizes Form Service', () => {
  let service: PrizesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrizesFormService);
  });

  describe('Service methods', () => {
    describe('createPrizesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrizesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            articleCode: expect.any(Object),
            levelCup: expect.any(Object),
            numberPrize: expect.any(Object),
            createTime: expect.any(Object),
            createName: expect.any(Object),
            updateTime: expect.any(Object),
            updateName: expect.any(Object),
          }),
        );
      });

      it('passing IPrizes should create a new form with FormGroup', () => {
        const formGroup = service.createPrizesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            articleCode: expect.any(Object),
            levelCup: expect.any(Object),
            numberPrize: expect.any(Object),
            createTime: expect.any(Object),
            createName: expect.any(Object),
            updateTime: expect.any(Object),
            updateName: expect.any(Object),
          }),
        );
      });
    });

    describe('getPrizes', () => {
      it('should return NewPrizes for default Prizes initial value', () => {
        const formGroup = service.createPrizesFormGroup(sampleWithNewData);

        const prizes = service.getPrizes(formGroup) as any;

        expect(prizes).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrizes for empty Prizes initial value', () => {
        const formGroup = service.createPrizesFormGroup();

        const prizes = service.getPrizes(formGroup) as any;

        expect(prizes).toMatchObject({});
      });

      it('should return IPrizes', () => {
        const formGroup = service.createPrizesFormGroup(sampleWithRequiredData);

        const prizes = service.getPrizes(formGroup) as any;

        expect(prizes).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrizes should not enable id FormControl', () => {
        const formGroup = service.createPrizesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrizes should disable id FormControl', () => {
        const formGroup = service.createPrizesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
