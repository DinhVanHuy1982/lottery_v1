import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRoleFunctionAction, NewRoleFunctionAction } from '../role-function-action.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRoleFunctionAction for edit and NewRoleFunctionActionFormGroupInput for create.
 */
type RoleFunctionActionFormGroupInput = IRoleFunctionAction | PartialWithRequiredKeyOf<NewRoleFunctionAction>;

type RoleFunctionActionFormDefaults = Pick<NewRoleFunctionAction, 'id'>;

type RoleFunctionActionFormGroupContent = {
  id: FormControl<IRoleFunctionAction['id'] | NewRoleFunctionAction['id']>;
  roleFunctionCode: FormControl<IRoleFunctionAction['roleFunctionCode']>;
  actionCode: FormControl<IRoleFunctionAction['actionCode']>;
};

export type RoleFunctionActionFormGroup = FormGroup<RoleFunctionActionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RoleFunctionActionFormService {
  createRoleFunctionActionFormGroup(roleFunctionAction: RoleFunctionActionFormGroupInput = { id: null }): RoleFunctionActionFormGroup {
    const roleFunctionActionRawValue = {
      ...this.getFormDefaults(),
      ...roleFunctionAction,
    };
    return new FormGroup<RoleFunctionActionFormGroupContent>({
      id: new FormControl(
        { value: roleFunctionActionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      roleFunctionCode: new FormControl(roleFunctionActionRawValue.roleFunctionCode, {
        validators: [Validators.maxLength(50)],
      }),
      actionCode: new FormControl(roleFunctionActionRawValue.actionCode, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getRoleFunctionAction(form: RoleFunctionActionFormGroup): IRoleFunctionAction | NewRoleFunctionAction {
    return form.getRawValue() as IRoleFunctionAction | NewRoleFunctionAction;
  }

  resetForm(form: RoleFunctionActionFormGroup, roleFunctionAction: RoleFunctionActionFormGroupInput): void {
    const roleFunctionActionRawValue = { ...this.getFormDefaults(), ...roleFunctionAction };
    form.reset(
      {
        ...roleFunctionActionRawValue,
        id: { value: roleFunctionActionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RoleFunctionActionFormDefaults {
    return {
      id: null,
    };
  }
}
