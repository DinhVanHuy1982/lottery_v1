import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAppParams, NewAppParams } from '../app-params.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppParams for edit and NewAppParamsFormGroupInput for create.
 */
type AppParamsFormGroupInput = IAppParams | PartialWithRequiredKeyOf<NewAppParams>;

type AppParamsFormDefaults = Pick<NewAppParams, 'id'>;

type AppParamsFormGroupContent = {
  id: FormControl<IAppParams['id'] | NewAppParams['id']>;
  code: FormControl<IAppParams['code']>;
  value: FormControl<IAppParams['value']>;
  type: FormControl<IAppParams['type']>;
};

export type AppParamsFormGroup = FormGroup<AppParamsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppParamsFormService {
  createAppParamsFormGroup(appParams: AppParamsFormGroupInput = { id: null }): AppParamsFormGroup {
    const appParamsRawValue = {
      ...this.getFormDefaults(),
      ...appParams,
    };
    return new FormGroup<AppParamsFormGroupContent>({
      id: new FormControl(
        { value: appParamsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(appParamsRawValue.code, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      value: new FormControl(appParamsRawValue.value, {
        validators: [Validators.maxLength(150)],
      }),
      type: new FormControl(appParamsRawValue.type, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getAppParams(form: AppParamsFormGroup): IAppParams | NewAppParams {
    return form.getRawValue() as IAppParams | NewAppParams;
  }

  resetForm(form: AppParamsFormGroup, appParams: AppParamsFormGroupInput): void {
    const appParamsRawValue = { ...this.getFormDefaults(), ...appParams };
    form.reset(
      {
        ...appParamsRawValue,
        id: { value: appParamsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AppParamsFormDefaults {
    return {
      id: null,
    };
  }
}
