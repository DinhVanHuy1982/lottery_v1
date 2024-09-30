import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPrizes, NewPrizes } from '../prizes.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrizes for edit and NewPrizesFormGroupInput for create.
 */
type PrizesFormGroupInput = IPrizes | PartialWithRequiredKeyOf<NewPrizes>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPrizes | NewPrizes> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

type PrizesFormRawValue = FormValueOf<IPrizes>;

type NewPrizesFormRawValue = FormValueOf<NewPrizes>;

type PrizesFormDefaults = Pick<NewPrizes, 'id' | 'createTime' | 'updateTime'>;

type PrizesFormGroupContent = {
  id: FormControl<PrizesFormRawValue['id'] | NewPrizes['id']>;
  code: FormControl<PrizesFormRawValue['code']>;
  articleCode: FormControl<PrizesFormRawValue['articleCode']>;
  levelCup: FormControl<PrizesFormRawValue['levelCup']>;
  numberPrize: FormControl<PrizesFormRawValue['numberPrize']>;
  createTime: FormControl<PrizesFormRawValue['createTime']>;
  createName: FormControl<PrizesFormRawValue['createName']>;
  updateTime: FormControl<PrizesFormRawValue['updateTime']>;
  updateName: FormControl<PrizesFormRawValue['updateName']>;
};

export type PrizesFormGroup = FormGroup<PrizesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrizesFormService {
  createPrizesFormGroup(prizes: PrizesFormGroupInput = { id: null }): PrizesFormGroup {
    const prizesRawValue = this.convertPrizesToPrizesRawValue({
      ...this.getFormDefaults(),
      ...prizes,
    });
    return new FormGroup<PrizesFormGroupContent>({
      id: new FormControl(
        { value: prizesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(prizesRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
      articleCode: new FormControl(prizesRawValue.articleCode, {
        validators: [Validators.maxLength(50)],
      }),
      levelCup: new FormControl(prizesRawValue.levelCup, {
        validators: [Validators.maxLength(50)],
      }),
      numberPrize: new FormControl(prizesRawValue.numberPrize),
      createTime: new FormControl(prizesRawValue.createTime),
      createName: new FormControl(prizesRawValue.createName, {
        validators: [Validators.maxLength(50)],
      }),
      updateTime: new FormControl(prizesRawValue.updateTime),
      updateName: new FormControl(prizesRawValue.updateName, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getPrizes(form: PrizesFormGroup): IPrizes | NewPrizes {
    return this.convertPrizesRawValueToPrizes(form.getRawValue() as PrizesFormRawValue | NewPrizesFormRawValue);
  }

  resetForm(form: PrizesFormGroup, prizes: PrizesFormGroupInput): void {
    const prizesRawValue = this.convertPrizesToPrizesRawValue({ ...this.getFormDefaults(), ...prizes });
    form.reset(
      {
        ...prizesRawValue,
        id: { value: prizesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PrizesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
      updateTime: currentTime,
    };
  }

  private convertPrizesRawValueToPrizes(rawPrizes: PrizesFormRawValue | NewPrizesFormRawValue): IPrizes | NewPrizes {
    return {
      ...rawPrizes,
      createTime: dayjs(rawPrizes.createTime, DATE_TIME_FORMAT),
      updateTime: dayjs(rawPrizes.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertPrizesToPrizesRawValue(
    prizes: IPrizes | (Partial<NewPrizes> & PrizesFormDefaults),
  ): PrizesFormRawValue | PartialWithRequiredKeyOf<NewPrizesFormRawValue> {
    return {
      ...prizes,
      createTime: prizes.createTime ? prizes.createTime.format(DATE_TIME_FORMAT) : undefined,
      updateTime: prizes.updateTime ? prizes.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
