import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRoleFunctions, NewRoleFunctions } from '../role-functions.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRoleFunctions for edit and NewRoleFunctionsFormGroupInput for create.
 */
type RoleFunctionsFormGroupInput = IRoleFunctions | PartialWithRequiredKeyOf<NewRoleFunctions>;

type RoleFunctionsFormDefaults = Pick<NewRoleFunctions, 'id'>;

type RoleFunctionsFormGroupContent = {
  id: FormControl<IRoleFunctions['id'] | NewRoleFunctions['id']>;
  code: FormControl<IRoleFunctions['code']>;
  roleCode: FormControl<IRoleFunctions['roleCode']>;
  functionCode: FormControl<IRoleFunctions['functionCode']>;
};

export type RoleFunctionsFormGroup = FormGroup<RoleFunctionsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RoleFunctionsFormService {
  createRoleFunctionsFormGroup(roleFunctions: RoleFunctionsFormGroupInput = { id: null }): RoleFunctionsFormGroup {
    const roleFunctionsRawValue = {
      ...this.getFormDefaults(),
      ...roleFunctions,
    };
    return new FormGroup<RoleFunctionsFormGroupContent>({
      id: new FormControl(
        { value: roleFunctionsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(roleFunctionsRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
      roleCode: new FormControl(roleFunctionsRawValue.roleCode, {
        validators: [Validators.maxLength(50)],
      }),
      functionCode: new FormControl(roleFunctionsRawValue.functionCode, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getRoleFunctions(form: RoleFunctionsFormGroup): IRoleFunctions | NewRoleFunctions {
    return form.getRawValue() as IRoleFunctions | NewRoleFunctions;
  }

  resetForm(form: RoleFunctionsFormGroup, roleFunctions: RoleFunctionsFormGroupInput): void {
    const roleFunctionsRawValue = { ...this.getFormDefaults(), ...roleFunctions };
    form.reset(
      {
        ...roleFunctionsRawValue,
        id: { value: roleFunctionsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RoleFunctionsFormDefaults {
    return {
      id: null,
    };
  }
}
