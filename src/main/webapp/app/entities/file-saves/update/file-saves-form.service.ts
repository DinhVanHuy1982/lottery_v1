import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFileSaves, NewFileSaves } from '../file-saves.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFileSaves for edit and NewFileSavesFormGroupInput for create.
 */
type FileSavesFormGroupInput = IFileSaves | PartialWithRequiredKeyOf<NewFileSaves>;

type FileSavesFormDefaults = Pick<NewFileSaves, 'id'>;

type FileSavesFormGroupContent = {
  id: FormControl<IFileSaves['id'] | NewFileSaves['id']>;
  fileId: FormControl<IFileSaves['fileId']>;
  fileName: FormControl<IFileSaves['fileName']>;
  filePath: FormControl<IFileSaves['filePath']>;
};

export type FileSavesFormGroup = FormGroup<FileSavesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FileSavesFormService {
  createFileSavesFormGroup(fileSaves: FileSavesFormGroupInput = { id: null }): FileSavesFormGroup {
    const fileSavesRawValue = {
      ...this.getFormDefaults(),
      ...fileSaves,
    };
    return new FormGroup<FileSavesFormGroupContent>({
      id: new FormControl(
        { value: fileSavesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fileId: new FormControl(fileSavesRawValue.fileId, {
        validators: [Validators.maxLength(250)],
      }),
      fileName: new FormControl(fileSavesRawValue.fileName, {
        validators: [Validators.maxLength(500)],
      }),
      filePath: new FormControl(fileSavesRawValue.filePath, {
        validators: [Validators.maxLength(1000)],
      }),
    });
  }

  getFileSaves(form: FileSavesFormGroup): IFileSaves | NewFileSaves {
    return form.getRawValue() as IFileSaves | NewFileSaves;
  }

  resetForm(form: FileSavesFormGroup, fileSaves: FileSavesFormGroupInput): void {
    const fileSavesRawValue = { ...this.getFormDefaults(), ...fileSaves };
    form.reset(
      {
        ...fileSavesRawValue,
        id: { value: fileSavesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FileSavesFormDefaults {
    return {
      id: null,
    };
  }
}
