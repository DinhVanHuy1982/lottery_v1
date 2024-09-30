import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIntroduceArticle, NewIntroduceArticle } from '../introduce-article.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIntroduceArticle for edit and NewIntroduceArticleFormGroupInput for create.
 */
type IntroduceArticleFormGroupInput = IIntroduceArticle | PartialWithRequiredKeyOf<NewIntroduceArticle>;

type IntroduceArticleFormDefaults = Pick<NewIntroduceArticle, 'id'>;

type IntroduceArticleFormGroupContent = {
  id: FormControl<IIntroduceArticle['id'] | NewIntroduceArticle['id']>;
  code: FormControl<IIntroduceArticle['code']>;
  articleCode: FormControl<IIntroduceArticle['articleCode']>;
  title: FormControl<IIntroduceArticle['title']>;
  content: FormControl<IIntroduceArticle['content']>;
  fileId: FormControl<IIntroduceArticle['fileId']>;
};

export type IntroduceArticleFormGroup = FormGroup<IntroduceArticleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IntroduceArticleFormService {
  createIntroduceArticleFormGroup(introduceArticle: IntroduceArticleFormGroupInput = { id: null }): IntroduceArticleFormGroup {
    const introduceArticleRawValue = {
      ...this.getFormDefaults(),
      ...introduceArticle,
    };
    return new FormGroup<IntroduceArticleFormGroupContent>({
      id: new FormControl(
        { value: introduceArticleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(introduceArticleRawValue.code, {
        validators: [Validators.maxLength(50)],
      }),
      articleCode: new FormControl(introduceArticleRawValue.articleCode, {
        validators: [Validators.maxLength(50)],
      }),
      title: new FormControl(introduceArticleRawValue.title, {
        validators: [Validators.maxLength(500)],
      }),
      content: new FormControl(introduceArticleRawValue.content, {
        validators: [Validators.maxLength(2000)],
      }),
      fileId: new FormControl(introduceArticleRawValue.fileId, {
        validators: [Validators.maxLength(250)],
      }),
    });
  }

  getIntroduceArticle(form: IntroduceArticleFormGroup): IIntroduceArticle | NewIntroduceArticle {
    return form.getRawValue() as IIntroduceArticle | NewIntroduceArticle;
  }

  resetForm(form: IntroduceArticleFormGroup, introduceArticle: IntroduceArticleFormGroupInput): void {
    const introduceArticleRawValue = { ...this.getFormDefaults(), ...introduceArticle };
    form.reset(
      {
        ...introduceArticleRawValue,
        id: { value: introduceArticleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IntroduceArticleFormDefaults {
    return {
      id: null,
    };
  }
}
