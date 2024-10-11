import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILevelDeposits, NewLevelDeposits } from '../level-deposits.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILevelDeposits for edit and NewLevelDepositsFormGroupInput for create.
 */
type LevelDepositsFormGroupInput = ILevelDeposits | PartialWithRequiredKeyOf<NewLevelDeposits>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILevelDeposits | NewLevelDeposits> = Omit<T, 'updateTime'> & {
  updateTime?: string | null;
};

type LevelDepositsFormRawValue = FormValueOf<ILevelDeposits>;

type NewLevelDepositsFormRawValue = FormValueOf<NewLevelDeposits>;

type LevelDepositsFormDefaults = Pick<NewLevelDeposits, 'id' | 'updateTime'>;

type LevelDepositsFormGroupContent = {
  id: FormControl<LevelDepositsFormRawValue['id'] | NewLevelDeposits['id']>;
  code: FormControl<LevelDepositsFormRawValue['code']>;
  minPrice: FormControl<LevelDepositsFormRawValue['minPrice']>;
  updateName: FormControl<LevelDepositsFormRawValue['updateName']>;
  updateTime: FormControl<LevelDepositsFormRawValue['updateTime']>;
  articleCode: FormControl<LevelDepositsFormRawValue['articleCode']>;
};

export type LevelDepositsFormGroup = FormGroup<LevelDepositsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LevelDepositsFormService {
  createLevelDepositsFormGroup(levelDeposits: LevelDepositsFormGroupInput = { id: null }): LevelDepositsFormGroup {
    const levelDepositsRawValue = this.convertLevelDepositsToLevelDepositsRawValue({
      ...this.getFormDefaults(),
      ...levelDeposits,
    });
    return new FormGroup<LevelDepositsFormGroupContent>({
      id: new FormControl(
        { value: levelDepositsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(levelDepositsRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
      minPrice: new FormControl(levelDepositsRawValue.minPrice),
      updateName: new FormControl(levelDepositsRawValue.updateName, {
        validators: [Validators.maxLength(50)],
      }),
      updateTime: new FormControl(levelDepositsRawValue.updateTime),
      articleCode: new FormControl(levelDepositsRawValue.articleCode, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getLevelDeposits(form: LevelDepositsFormGroup): ILevelDeposits | NewLevelDeposits {
    return this.convertLevelDepositsRawValueToLevelDeposits(form.getRawValue() as LevelDepositsFormRawValue | NewLevelDepositsFormRawValue);
  }

  resetForm(form: LevelDepositsFormGroup, levelDeposits: LevelDepositsFormGroupInput): void {
    const levelDepositsRawValue = this.convertLevelDepositsToLevelDepositsRawValue({ ...this.getFormDefaults(), ...levelDeposits });
    form.reset(
      {
        ...levelDepositsRawValue,
        id: { value: levelDepositsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LevelDepositsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      updateTime: currentTime,
    };
  }

  private convertLevelDepositsRawValueToLevelDeposits(
    rawLevelDeposits: LevelDepositsFormRawValue | NewLevelDepositsFormRawValue,
  ): ILevelDeposits | NewLevelDeposits {
    return {
      ...rawLevelDeposits,
      updateTime: dayjs(rawLevelDeposits.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertLevelDepositsToLevelDepositsRawValue(
    levelDeposits: ILevelDeposits | (Partial<NewLevelDeposits> & LevelDepositsFormDefaults),
  ): LevelDepositsFormRawValue | PartialWithRequiredKeyOf<NewLevelDepositsFormRawValue> {
    return {
      ...levelDeposits,
      updateTime: levelDeposits.updateTime ? levelDeposits.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
