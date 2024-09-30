import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IResultsEveryDay, NewResultsEveryDay } from '../results-every-day.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResultsEveryDay for edit and NewResultsEveryDayFormGroupInput for create.
 */
type ResultsEveryDayFormGroupInput = IResultsEveryDay | PartialWithRequiredKeyOf<NewResultsEveryDay>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IResultsEveryDay | NewResultsEveryDay> = Omit<T, 'resultDate'> & {
  resultDate?: string | null;
};

type ResultsEveryDayFormRawValue = FormValueOf<IResultsEveryDay>;

type NewResultsEveryDayFormRawValue = FormValueOf<NewResultsEveryDay>;

type ResultsEveryDayFormDefaults = Pick<NewResultsEveryDay, 'id' | 'resultDate'>;

type ResultsEveryDayFormGroupContent = {
  id: FormControl<ResultsEveryDayFormRawValue['id'] | NewResultsEveryDay['id']>;
  resultDate: FormControl<ResultsEveryDayFormRawValue['resultDate']>;
  prizeCode: FormControl<ResultsEveryDayFormRawValue['prizeCode']>;
  result: FormControl<ResultsEveryDayFormRawValue['result']>;
};

export type ResultsEveryDayFormGroup = FormGroup<ResultsEveryDayFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResultsEveryDayFormService {
  createResultsEveryDayFormGroup(resultsEveryDay: ResultsEveryDayFormGroupInput = { id: null }): ResultsEveryDayFormGroup {
    const resultsEveryDayRawValue = this.convertResultsEveryDayToResultsEveryDayRawValue({
      ...this.getFormDefaults(),
      ...resultsEveryDay,
    });
    return new FormGroup<ResultsEveryDayFormGroupContent>({
      id: new FormControl(
        { value: resultsEveryDayRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      resultDate: new FormControl(resultsEveryDayRawValue.resultDate),
      prizeCode: new FormControl(resultsEveryDayRawValue.prizeCode, {
        validators: [Validators.maxLength(50)],
      }),
      result: new FormControl(resultsEveryDayRawValue.result, {
        validators: [Validators.maxLength(100)],
      }),
    });
  }

  getResultsEveryDay(form: ResultsEveryDayFormGroup): IResultsEveryDay | NewResultsEveryDay {
    return this.convertResultsEveryDayRawValueToResultsEveryDay(
      form.getRawValue() as ResultsEveryDayFormRawValue | NewResultsEveryDayFormRawValue,
    );
  }

  resetForm(form: ResultsEveryDayFormGroup, resultsEveryDay: ResultsEveryDayFormGroupInput): void {
    const resultsEveryDayRawValue = this.convertResultsEveryDayToResultsEveryDayRawValue({ ...this.getFormDefaults(), ...resultsEveryDay });
    form.reset(
      {
        ...resultsEveryDayRawValue,
        id: { value: resultsEveryDayRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ResultsEveryDayFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      resultDate: currentTime,
    };
  }

  private convertResultsEveryDayRawValueToResultsEveryDay(
    rawResultsEveryDay: ResultsEveryDayFormRawValue | NewResultsEveryDayFormRawValue,
  ): IResultsEveryDay | NewResultsEveryDay {
    return {
      ...rawResultsEveryDay,
      resultDate: dayjs(rawResultsEveryDay.resultDate, DATE_TIME_FORMAT),
    };
  }

  private convertResultsEveryDayToResultsEveryDayRawValue(
    resultsEveryDay: IResultsEveryDay | (Partial<NewResultsEveryDay> & ResultsEveryDayFormDefaults),
  ): ResultsEveryDayFormRawValue | PartialWithRequiredKeyOf<NewResultsEveryDayFormRawValue> {
    return {
      ...resultsEveryDay,
      resultDate: resultsEveryDay.resultDate ? resultsEveryDay.resultDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
