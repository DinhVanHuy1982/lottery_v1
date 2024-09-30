import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IArticleGroup } from '../article-group.model';
import { ArticleGroupService } from '../service/article-group.service';
import { ArticleGroupFormService, ArticleGroupFormGroup } from './article-group-form.service';

@Component({
  standalone: true,
  selector: 'jhi-article-group-update',
  templateUrl: './article-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ArticleGroupUpdateComponent implements OnInit {
  isSaving = false;
  articleGroup: IArticleGroup | null = null;

  editForm: ArticleGroupFormGroup = this.articleGroupFormService.createArticleGroupFormGroup();

  constructor(
    protected articleGroupService: ArticleGroupService,
    protected articleGroupFormService: ArticleGroupFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articleGroup }) => {
      this.articleGroup = articleGroup;
      if (articleGroup) {
        this.updateForm(articleGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const articleGroup = this.articleGroupFormService.getArticleGroup(this.editForm);
    if (articleGroup.id !== null) {
      this.subscribeToSaveResponse(this.articleGroupService.update(articleGroup));
    } else {
      this.subscribeToSaveResponse(this.articleGroupService.create(articleGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticleGroup>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(articleGroup: IArticleGroup): void {
    this.articleGroup = articleGroup;
    this.articleGroupFormService.resetForm(this.editForm, articleGroup);
  }
}
