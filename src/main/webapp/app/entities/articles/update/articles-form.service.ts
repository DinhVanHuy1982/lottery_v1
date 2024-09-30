import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArticles, NewArticles } from '../articles.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArticles for edit and NewArticlesFormGroupInput for create.
 */
type ArticlesFormGroupInput = IArticles | PartialWithRequiredKeyOf<NewArticles>;

type ArticlesFormDefaults = Pick<NewArticles, 'id'>;

type ArticlesFormGroupContent = {
  id: FormControl<IArticles['id'] | NewArticles['id']>;
  code: FormControl<IArticles['code']>;
  title: FormControl<IArticles['title']>;
  content: FormControl<IArticles['content']>;
  fileId: FormControl<IArticles['fileId']>;
  updateName: FormControl<IArticles['updateName']>;
  numberChoice: FormControl<IArticles['numberChoice']>;
  numberOfDigits: FormControl<IArticles['numberOfDigits']>;
  timeStart: FormControl<IArticles['timeStart']>;
  timeEnd: FormControl<IArticles['timeEnd']>;
};

export type ArticlesFormGroup = FormGroup<ArticlesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArticlesFormService {
  createArticlesFormGroup(articles: ArticlesFormGroupInput = { id: null }): ArticlesFormGroup {
    const articlesRawValue = {
      ...this.getFormDefaults(),
      ...articles,
    };
    return new FormGroup<ArticlesFormGroupContent>({
      id: new FormControl(
        { value: articlesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(articlesRawValue.code, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      title: new FormControl(articlesRawValue.title, {
        validators: [Validators.maxLength(500)],
      }),
      content: new FormControl(articlesRawValue.content, {
        validators: [Validators.maxLength(2000)],
      }),
      fileId: new FormControl(articlesRawValue.fileId, {
        validators: [Validators.maxLength(250)],
      }),
      updateName: new FormControl(articlesRawValue.updateName, {
        validators: [Validators.maxLength(50)],
      }),
      numberChoice: new FormControl(articlesRawValue.numberChoice),
      numberOfDigits: new FormControl(articlesRawValue.numberOfDigits),
      timeStart: new FormControl(articlesRawValue.timeStart),
      timeEnd: new FormControl(articlesRawValue.timeEnd),
    });
  }

  getArticles(form: ArticlesFormGroup): IArticles | NewArticles {
    return form.getRawValue() as IArticles | NewArticles;
  }

  resetForm(form: ArticlesFormGroup, articles: ArticlesFormGroupInput): void {
    const articlesRawValue = { ...this.getFormDefaults(), ...articles };
    form.reset(
      {
        ...articlesRawValue,
        id: { value: articlesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ArticlesFormDefaults {
    return {
      id: null,
    };
  }
}
