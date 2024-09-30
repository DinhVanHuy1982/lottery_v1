import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IArticleGroup, NewArticleGroup } from '../article-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArticleGroup for edit and NewArticleGroupFormGroupInput for create.
 */
type ArticleGroupFormGroupInput = IArticleGroup | PartialWithRequiredKeyOf<NewArticleGroup>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IArticleGroup | NewArticleGroup> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

type ArticleGroupFormRawValue = FormValueOf<IArticleGroup>;

type NewArticleGroupFormRawValue = FormValueOf<NewArticleGroup>;

type ArticleGroupFormDefaults = Pick<NewArticleGroup, 'id' | 'createTime' | 'updateTime'>;

type ArticleGroupFormGroupContent = {
  id: FormControl<ArticleGroupFormRawValue['id'] | NewArticleGroup['id']>;
  code: FormControl<ArticleGroupFormRawValue['code']>;
  title: FormControl<ArticleGroupFormRawValue['title']>;
  mainContent: FormControl<ArticleGroupFormRawValue['mainContent']>;
  createTime: FormControl<ArticleGroupFormRawValue['createTime']>;
  updateTime: FormControl<ArticleGroupFormRawValue['updateTime']>;
  createName: FormControl<ArticleGroupFormRawValue['createName']>;
  updateName: FormControl<ArticleGroupFormRawValue['updateName']>;
  fileName: FormControl<ArticleGroupFormRawValue['fileName']>;
  filePath: FormControl<ArticleGroupFormRawValue['filePath']>;
  fileId: FormControl<ArticleGroupFormRawValue['fileId']>;
};

export type ArticleGroupFormGroup = FormGroup<ArticleGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArticleGroupFormService {
  createArticleGroupFormGroup(articleGroup: ArticleGroupFormGroupInput = { id: null }): ArticleGroupFormGroup {
    const articleGroupRawValue = this.convertArticleGroupToArticleGroupRawValue({
      ...this.getFormDefaults(),
      ...articleGroup,
    });
    return new FormGroup<ArticleGroupFormGroupContent>({
      id: new FormControl(
        { value: articleGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(articleGroupRawValue.code, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      title: new FormControl(articleGroupRawValue.title, {
        validators: [Validators.maxLength(500)],
      }),
      mainContent: new FormControl(articleGroupRawValue.mainContent, {
        validators: [Validators.maxLength(2000)],
      }),
      createTime: new FormControl(articleGroupRawValue.createTime),
      updateTime: new FormControl(articleGroupRawValue.updateTime),
      createName: new FormControl(articleGroupRawValue.createName, {
        validators: [Validators.maxLength(150)],
      }),
      updateName: new FormControl(articleGroupRawValue.updateName, {
        validators: [Validators.maxLength(150)],
      }),
      fileName: new FormControl(articleGroupRawValue.fileName, {
        validators: [Validators.maxLength(500)],
      }),
      filePath: new FormControl(articleGroupRawValue.filePath, {
        validators: [Validators.maxLength(1000)],
      }),
      fileId: new FormControl(articleGroupRawValue.fileId, {
        validators: [Validators.maxLength(250)],
      }),
    });
  }

  getArticleGroup(form: ArticleGroupFormGroup): IArticleGroup | NewArticleGroup {
    return this.convertArticleGroupRawValueToArticleGroup(form.getRawValue() as ArticleGroupFormRawValue | NewArticleGroupFormRawValue);
  }

  resetForm(form: ArticleGroupFormGroup, articleGroup: ArticleGroupFormGroupInput): void {
    const articleGroupRawValue = this.convertArticleGroupToArticleGroupRawValue({ ...this.getFormDefaults(), ...articleGroup });
    form.reset(
      {
        ...articleGroupRawValue,
        id: { value: articleGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ArticleGroupFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
      updateTime: currentTime,
    };
  }

  private convertArticleGroupRawValueToArticleGroup(
    rawArticleGroup: ArticleGroupFormRawValue | NewArticleGroupFormRawValue,
  ): IArticleGroup | NewArticleGroup {
    return {
      ...rawArticleGroup,
      createTime: dayjs(rawArticleGroup.createTime, DATE_TIME_FORMAT),
      updateTime: dayjs(rawArticleGroup.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertArticleGroupToArticleGroupRawValue(
    articleGroup: IArticleGroup | (Partial<NewArticleGroup> & ArticleGroupFormDefaults),
  ): ArticleGroupFormRawValue | PartialWithRequiredKeyOf<NewArticleGroupFormRawValue> {
    return {
      ...articleGroup,
      createTime: articleGroup.createTime ? articleGroup.createTime.format(DATE_TIME_FORMAT) : undefined,
      updateTime: articleGroup.updateTime ? articleGroup.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
