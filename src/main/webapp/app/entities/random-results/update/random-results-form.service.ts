import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRandomResults, NewRandomResults } from '../random-results.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRandomResults for edit and NewRandomResultsFormGroupInput for create.
 */
type RandomResultsFormGroupInput = IRandomResults | PartialWithRequiredKeyOf<NewRandomResults>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRandomResults | NewRandomResults> = Omit<T, 'randomDate'> & {
  randomDate?: string | null;
};

type RandomResultsFormRawValue = FormValueOf<IRandomResults>;

type NewRandomResultsFormRawValue = FormValueOf<NewRandomResults>;

type RandomResultsFormDefaults = Pick<NewRandomResults, 'id' | 'randomDate'>;

type RandomResultsFormGroupContent = {
  id: FormControl<RandomResultsFormRawValue['id'] | NewRandomResults['id']>;
  randomDate: FormControl<RandomResultsFormRawValue['randomDate']>;
  prizeCode: FormControl<RandomResultsFormRawValue['prizeCode']>;
  result: FormControl<RandomResultsFormRawValue['result']>;
  randomUserPlay: FormControl<RandomResultsFormRawValue['randomUserPlay']>;
  userPlay: FormControl<RandomResultsFormRawValue['userPlay']>;
  randomUserSuccess: FormControl<RandomResultsFormRawValue['randomUserSuccess']>;
  userSuccess: FormControl<RandomResultsFormRawValue['userSuccess']>;
};

export type RandomResultsFormGroup = FormGroup<RandomResultsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RandomResultsFormService {
  createRandomResultsFormGroup(randomResults: RandomResultsFormGroupInput = { id: null }): RandomResultsFormGroup {
    const randomResultsRawValue = this.convertRandomResultsToRandomResultsRawValue({
      ...this.getFormDefaults(),
      ...randomResults,
    });
    return new FormGroup<RandomResultsFormGroupContent>({
      id: new FormControl(
        { value: randomResultsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      randomDate: new FormControl(randomResultsRawValue.randomDate),
      prizeCode: new FormControl(randomResultsRawValue.prizeCode, {
        validators: [Validators.maxLength(50)],
      }),
      result: new FormControl(randomResultsRawValue.result, {
        validators: [Validators.maxLength(100)],
      }),
      randomUserPlay: new FormControl(randomResultsRawValue.randomUserPlay),
      userPlay: new FormControl(randomResultsRawValue.userPlay),
      randomUserSuccess: new FormControl(randomResultsRawValue.randomUserSuccess),
      userSuccess: new FormControl(randomResultsRawValue.userSuccess),
    });
  }

  getRandomResults(form: RandomResultsFormGroup): IRandomResults | NewRandomResults {
    return this.convertRandomResultsRawValueToRandomResults(form.getRawValue() as RandomResultsFormRawValue | NewRandomResultsFormRawValue);
  }

  resetForm(form: RandomResultsFormGroup, randomResults: RandomResultsFormGroupInput): void {
    const randomResultsRawValue = this.convertRandomResultsToRandomResultsRawValue({ ...this.getFormDefaults(), ...randomResults });
    form.reset(
      {
        ...randomResultsRawValue,
        id: { value: randomResultsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RandomResultsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      randomDate: currentTime,
    };
  }

  private convertRandomResultsRawValueToRandomResults(
    rawRandomResults: RandomResultsFormRawValue | NewRandomResultsFormRawValue,
  ): IRandomResults | NewRandomResults {
    return {
      ...rawRandomResults,
      randomDate: dayjs(rawRandomResults.randomDate, DATE_TIME_FORMAT),
    };
  }

  private convertRandomResultsToRandomResultsRawValue(
    randomResults: IRandomResults | (Partial<NewRandomResults> & RandomResultsFormDefaults),
  ): RandomResultsFormRawValue | PartialWithRequiredKeyOf<NewRandomResultsFormRawValue> {
    return {
      ...randomResults,
      randomDate: randomResults.randomDate ? randomResults.randomDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
