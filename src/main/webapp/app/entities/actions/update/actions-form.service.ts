import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IActions, NewActions } from '../actions.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IActions for edit and NewActionsFormGroupInput for create.
 */
type ActionsFormGroupInput = IActions | PartialWithRequiredKeyOf<NewActions>;

type ActionsFormDefaults = Pick<NewActions, 'id'>;

type ActionsFormGroupContent = {
  id: FormControl<IActions['id'] | NewActions['id']>;
  name: FormControl<IActions['name']>;
  code: FormControl<IActions['code']>;
};

export type ActionsFormGroup = FormGroup<ActionsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ActionsFormService {
  createActionsFormGroup(actions: ActionsFormGroupInput = { id: null }): ActionsFormGroup {
    const actionsRawValue = {
      ...this.getFormDefaults(),
      ...actions,
    };
    return new FormGroup<ActionsFormGroupContent>({
      id: new FormControl(
        { value: actionsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(actionsRawValue.name, {
        validators: [Validators.maxLength(100)],
      }),
      code: new FormControl(actionsRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
    });
  }

  getActions(form: ActionsFormGroup): IActions | NewActions {
    return form.getRawValue() as IActions | NewActions;
  }

  resetForm(form: ActionsFormGroup, actions: ActionsFormGroupInput): void {
    const actionsRawValue = { ...this.getFormDefaults(), ...actions };
    form.reset(
      {
        ...actionsRawValue,
        id: { value: actionsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ActionsFormDefaults {
    return {
      id: null,
    };
  }
}
