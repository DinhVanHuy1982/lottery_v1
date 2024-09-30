import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFunctions, NewFunctions } from '../functions.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFunctions for edit and NewFunctionsFormGroupInput for create.
 */
type FunctionsFormGroupInput = IFunctions | PartialWithRequiredKeyOf<NewFunctions>;

type FunctionsFormDefaults = Pick<NewFunctions, 'id'>;

type FunctionsFormGroupContent = {
  id: FormControl<IFunctions['id'] | NewFunctions['id']>;
  name: FormControl<IFunctions['name']>;
  code: FormControl<IFunctions['code']>;
};

export type FunctionsFormGroup = FormGroup<FunctionsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FunctionsFormService {
  createFunctionsFormGroup(functions: FunctionsFormGroupInput = { id: null }): FunctionsFormGroup {
    const functionsRawValue = {
      ...this.getFormDefaults(),
      ...functions,
    };
    return new FormGroup<FunctionsFormGroupContent>({
      id: new FormControl(
        { value: functionsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(functionsRawValue.name, {
        validators: [Validators.maxLength(100)],
      }),
      code: new FormControl(functionsRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getFunctions(form: FunctionsFormGroup): IFunctions | NewFunctions {
    return form.getRawValue() as IFunctions | NewFunctions;
  }

  resetForm(form: FunctionsFormGroup, functions: FunctionsFormGroupInput): void {
    const functionsRawValue = { ...this.getFormDefaults(), ...functions };
    form.reset(
      {
        ...functionsRawValue,
        id: { value: functionsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FunctionsFormDefaults {
    return {
      id: null,
    };
  }
}
