import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDeposits, NewDeposits } from '../deposits.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDeposits for edit and NewDepositsFormGroupInput for create.
 */
type DepositsFormGroupInput = IDeposits | PartialWithRequiredKeyOf<NewDeposits>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDeposits | NewDeposits> = Omit<T, 'createTime'> & {
  createTime?: string | null;
};

type DepositsFormRawValue = FormValueOf<IDeposits>;

type NewDepositsFormRawValue = FormValueOf<NewDeposits>;

type DepositsFormDefaults = Pick<NewDeposits, 'id' | 'createTime'>;

type DepositsFormGroupContent = {
  id: FormControl<DepositsFormRawValue['id'] | NewDeposits['id']>;
  articleCode: FormControl<DepositsFormRawValue['articleCode']>;
  netwrokCard: FormControl<DepositsFormRawValue['netwrokCard']>;
  valueCard: FormControl<DepositsFormRawValue['valueCard']>;
  createTime: FormControl<DepositsFormRawValue['createTime']>;
  seriCard: FormControl<DepositsFormRawValue['seriCard']>;
  codeCard: FormControl<DepositsFormRawValue['codeCard']>;
  status: FormControl<DepositsFormRawValue['status']>;
  userAppose: FormControl<DepositsFormRawValue['userAppose']>;
  valueChoice: FormControl<DepositsFormRawValue['valueChoice']>;
  phoneNumber: FormControl<DepositsFormRawValue['phoneNumber']>;
};

export type DepositsFormGroup = FormGroup<DepositsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepositsFormService {
  createDepositsFormGroup(deposits: DepositsFormGroupInput = { id: null }): DepositsFormGroup {
    const depositsRawValue = this.convertDepositsToDepositsRawValue({
      ...this.getFormDefaults(),
      ...deposits,
    });
    return new FormGroup<DepositsFormGroupContent>({
      id: new FormControl(
        { value: depositsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      articleCode: new FormControl(depositsRawValue.articleCode, {
        validators: [Validators.maxLength(50)],
      }),
      netwrokCard: new FormControl(depositsRawValue.netwrokCard, {
        validators: [Validators.maxLength(50)],
      }),
      valueCard: new FormControl(depositsRawValue.valueCard, {
        validators: [Validators.maxLength(50)],
      }),
      createTime: new FormControl(depositsRawValue.createTime),
      seriCard: new FormControl(depositsRawValue.seriCard, {
        validators: [Validators.maxLength(50)],
      }),
      codeCard: new FormControl(depositsRawValue.codeCard, {
        validators: [Validators.maxLength(50)],
      }),
      status: new FormControl(depositsRawValue.status),
      userAppose: new FormControl(depositsRawValue.userAppose, {
        validators: [Validators.maxLength(50)],
      }),
      valueChoice: new FormControl(depositsRawValue.valueChoice, {
        validators: [Validators.maxLength(100)],
      }),
      phoneNumber: new FormControl(depositsRawValue.phoneNumber, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getDeposits(form: DepositsFormGroup): IDeposits | NewDeposits {
    return this.convertDepositsRawValueToDeposits(form.getRawValue() as DepositsFormRawValue | NewDepositsFormRawValue);
  }

  resetForm(form: DepositsFormGroup, deposits: DepositsFormGroupInput): void {
    const depositsRawValue = this.convertDepositsToDepositsRawValue({ ...this.getFormDefaults(), ...deposits });
    form.reset(
      {
        ...depositsRawValue,
        id: { value: depositsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DepositsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
    };
  }

  private convertDepositsRawValueToDeposits(rawDeposits: DepositsFormRawValue | NewDepositsFormRawValue): IDeposits | NewDeposits {
    return {
      ...rawDeposits,
      createTime: dayjs(rawDeposits.createTime, DATE_TIME_FORMAT),
    };
  }

  private convertDepositsToDepositsRawValue(
    deposits: IDeposits | (Partial<NewDeposits> & DepositsFormDefaults),
  ): DepositsFormRawValue | PartialWithRequiredKeyOf<NewDepositsFormRawValue> {
    return {
      ...deposits,
      createTime: deposits.createTime ? deposits.createTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
