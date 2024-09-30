import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRoles, NewRoles } from '../roles.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRoles for edit and NewRolesFormGroupInput for create.
 */
type RolesFormGroupInput = IRoles | PartialWithRequiredKeyOf<NewRoles>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRoles | NewRoles> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

type RolesFormRawValue = FormValueOf<IRoles>;

type NewRolesFormRawValue = FormValueOf<NewRoles>;

type RolesFormDefaults = Pick<NewRoles, 'id' | 'createTime' | 'updateTime'>;

type RolesFormGroupContent = {
  id: FormControl<RolesFormRawValue['id'] | NewRoles['id']>;
  name: FormControl<RolesFormRawValue['name']>;
  code: FormControl<RolesFormRawValue['code']>;
  status: FormControl<RolesFormRawValue['status']>;
  description: FormControl<RolesFormRawValue['description']>;
  createTime: FormControl<RolesFormRawValue['createTime']>;
  createName: FormControl<RolesFormRawValue['createName']>;
  updateName: FormControl<RolesFormRawValue['updateName']>;
  updateTime: FormControl<RolesFormRawValue['updateTime']>;
};

export type RolesFormGroup = FormGroup<RolesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RolesFormService {
  createRolesFormGroup(roles: RolesFormGroupInput = { id: null }): RolesFormGroup {
    const rolesRawValue = this.convertRolesToRolesRawValue({
      ...this.getFormDefaults(),
      ...roles,
    });
    return new FormGroup<RolesFormGroupContent>({
      id: new FormControl(
        { value: rolesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(rolesRawValue.name, {
        validators: [Validators.maxLength(100)],
      }),
      code: new FormControl(rolesRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
      status: new FormControl(rolesRawValue.status),
      description: new FormControl(rolesRawValue.description, {
        validators: [Validators.maxLength(500)],
      }),
      createTime: new FormControl(rolesRawValue.createTime),
      createName: new FormControl(rolesRawValue.createName, {
        validators: [Validators.maxLength(50)],
      }),
      updateName: new FormControl(rolesRawValue.updateName, {
        validators: [Validators.maxLength(50)],
      }),
      updateTime: new FormControl(rolesRawValue.updateTime),
    });
  }

  getRoles(form: RolesFormGroup): IRoles | NewRoles {
    return this.convertRolesRawValueToRoles(form.getRawValue() as RolesFormRawValue | NewRolesFormRawValue);
  }

  resetForm(form: RolesFormGroup, roles: RolesFormGroupInput): void {
    const rolesRawValue = this.convertRolesToRolesRawValue({ ...this.getFormDefaults(), ...roles });
    form.reset(
      {
        ...rolesRawValue,
        id: { value: rolesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RolesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
      updateTime: currentTime,
    };
  }

  private convertRolesRawValueToRoles(rawRoles: RolesFormRawValue | NewRolesFormRawValue): IRoles | NewRoles {
    return {
      ...rawRoles,
      createTime: dayjs(rawRoles.createTime, DATE_TIME_FORMAT),
      updateTime: dayjs(rawRoles.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertRolesToRolesRawValue(
    roles: IRoles | (Partial<NewRoles> & RolesFormDefaults),
  ): RolesFormRawValue | PartialWithRequiredKeyOf<NewRolesFormRawValue> {
    return {
      ...roles,
      createTime: roles.createTime ? roles.createTime.format(DATE_TIME_FORMAT) : undefined,
      updateTime: roles.updateTime ? roles.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
