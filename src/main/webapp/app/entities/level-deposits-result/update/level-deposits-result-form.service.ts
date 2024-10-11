import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILevelDepositsResult, NewLevelDepositsResult } from '../level-deposits-result.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILevelDepositsResult for edit and NewLevelDepositsResultFormGroupInput for create.
 */
type LevelDepositsResultFormGroupInput = ILevelDepositsResult | PartialWithRequiredKeyOf<NewLevelDepositsResult>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILevelDepositsResult | NewLevelDepositsResult> = Omit<T, 'resultDate'> & {
  resultDate?: string | null;
};

type LevelDepositsResultFormRawValue = FormValueOf<ILevelDepositsResult>;

type NewLevelDepositsResultFormRawValue = FormValueOf<NewLevelDepositsResult>;

type LevelDepositsResultFormDefaults = Pick<NewLevelDepositsResult, 'id' | 'resultDate'>;

type LevelDepositsResultFormGroupContent = {
  id: FormControl<LevelDepositsResultFormRawValue['id'] | NewLevelDepositsResult['id']>;
  code: FormControl<LevelDepositsResultFormRawValue['code']>;
  levelDepositsCode: FormControl<LevelDepositsResultFormRawValue['levelDepositsCode']>;
  randomResultCode: FormControl<LevelDepositsResultFormRawValue['randomResultCode']>;
  resultDate: FormControl<LevelDepositsResultFormRawValue['resultDate']>;
};

export type LevelDepositsResultFormGroup = FormGroup<LevelDepositsResultFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LevelDepositsResultFormService {
  createLevelDepositsResultFormGroup(levelDepositsResult: LevelDepositsResultFormGroupInput = { id: null }): LevelDepositsResultFormGroup {
    const levelDepositsResultRawValue = this.convertLevelDepositsResultToLevelDepositsResultRawValue({
      ...this.getFormDefaults(),
      ...levelDepositsResult,
    });
    return new FormGroup<LevelDepositsResultFormGroupContent>({
      id: new FormControl(
        { value: levelDepositsResultRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(levelDepositsResultRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
      levelDepositsCode: new FormControl(levelDepositsResultRawValue.levelDepositsCode, {
        validators: [Validators.maxLength(50)],
      }),
      randomResultCode: new FormControl(levelDepositsResultRawValue.randomResultCode, {
        validators: [Validators.maxLength(50)],
      }),
      resultDate: new FormControl(levelDepositsResultRawValue.resultDate),
    });
  }

  getLevelDepositsResult(form: LevelDepositsResultFormGroup): ILevelDepositsResult | NewLevelDepositsResult {
    return this.convertLevelDepositsResultRawValueToLevelDepositsResult(
      form.getRawValue() as LevelDepositsResultFormRawValue | NewLevelDepositsResultFormRawValue,
    );
  }

  resetForm(form: LevelDepositsResultFormGroup, levelDepositsResult: LevelDepositsResultFormGroupInput): void {
    const levelDepositsResultRawValue = this.convertLevelDepositsResultToLevelDepositsResultRawValue({
      ...this.getFormDefaults(),
      ...levelDepositsResult,
    });
    form.reset(
      {
        ...levelDepositsResultRawValue,
        id: { value: levelDepositsResultRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LevelDepositsResultFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      resultDate: currentTime,
    };
  }

  private convertLevelDepositsResultRawValueToLevelDepositsResult(
    rawLevelDepositsResult: LevelDepositsResultFormRawValue | NewLevelDepositsResultFormRawValue,
  ): ILevelDepositsResult | NewLevelDepositsResult {
    return {
      ...rawLevelDepositsResult,
      resultDate: dayjs(rawLevelDepositsResult.resultDate, DATE_TIME_FORMAT),
    };
  }

  private convertLevelDepositsResultToLevelDepositsResultRawValue(
    levelDepositsResult: ILevelDepositsResult | (Partial<NewLevelDepositsResult> & LevelDepositsResultFormDefaults),
  ): LevelDepositsResultFormRawValue | PartialWithRequiredKeyOf<NewLevelDepositsResultFormRawValue> {
    return {
      ...levelDepositsResult,
      resultDate: levelDepositsResult.resultDate ? levelDepositsResult.resultDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
