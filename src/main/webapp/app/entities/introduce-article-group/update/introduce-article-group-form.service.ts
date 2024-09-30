import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIntroduceArticleGroup, NewIntroduceArticleGroup } from '../introduce-article-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIntroduceArticleGroup for edit and NewIntroduceArticleGroupFormGroupInput for create.
 */
type IntroduceArticleGroupFormGroupInput = IIntroduceArticleGroup | PartialWithRequiredKeyOf<NewIntroduceArticleGroup>;

type IntroduceArticleGroupFormDefaults = Pick<NewIntroduceArticleGroup, 'id'>;

type IntroduceArticleGroupFormGroupContent = {
  id: FormControl<IIntroduceArticleGroup['id'] | NewIntroduceArticleGroup['id']>;
  code: FormControl<IIntroduceArticleGroup['code']>;
  articleGroupCode: FormControl<IIntroduceArticleGroup['articleGroupCode']>;
  fileId: FormControl<IIntroduceArticleGroup['fileId']>;
  titleIntroduce: FormControl<IIntroduceArticleGroup['titleIntroduce']>;
  contentIntroduce: FormControl<IIntroduceArticleGroup['contentIntroduce']>;
};

export type IntroduceArticleGroupFormGroup = FormGroup<IntroduceArticleGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IntroduceArticleGroupFormService {
  createIntroduceArticleGroupFormGroup(
    introduceArticleGroup: IntroduceArticleGroupFormGroupInput = { id: null },
  ): IntroduceArticleGroupFormGroup {
    const introduceArticleGroupRawValue = {
      ...this.getFormDefaults(),
      ...introduceArticleGroup,
    };
    return new FormGroup<IntroduceArticleGroupFormGroupContent>({
      id: new FormControl(
        { value: introduceArticleGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(introduceArticleGroupRawValue.code, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      articleGroupCode: new FormControl(introduceArticleGroupRawValue.articleGroupCode, {
        validators: [Validators.maxLength(50)],
      }),
      fileId: new FormControl(introduceArticleGroupRawValue.fileId, {
        validators: [Validators.maxLength(250)],
      }),
      titleIntroduce: new FormControl(introduceArticleGroupRawValue.titleIntroduce, {
        validators: [Validators.maxLength(500)],
      }),
      contentIntroduce: new FormControl(introduceArticleGroupRawValue.contentIntroduce, {
        validators: [Validators.maxLength(2000)],
      }),
    });
  }

  getIntroduceArticleGroup(form: IntroduceArticleGroupFormGroup): IIntroduceArticleGroup | NewIntroduceArticleGroup {
    return form.getRawValue() as IIntroduceArticleGroup | NewIntroduceArticleGroup;
  }

  resetForm(form: IntroduceArticleGroupFormGroup, introduceArticleGroup: IntroduceArticleGroupFormGroupInput): void {
    const introduceArticleGroupRawValue = { ...this.getFormDefaults(), ...introduceArticleGroup };
    form.reset(
      {
        ...introduceArticleGroupRawValue,
        id: { value: introduceArticleGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IntroduceArticleGroupFormDefaults {
    return {
      id: null,
    };
  }
}
