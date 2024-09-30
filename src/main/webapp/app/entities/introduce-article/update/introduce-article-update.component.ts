import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IIntroduceArticle } from '../introduce-article.model';
import { IntroduceArticleService } from '../service/introduce-article.service';
import { IntroduceArticleFormService, IntroduceArticleFormGroup } from './introduce-article-form.service';

@Component({
  standalone: true,
  selector: 'jhi-introduce-article-update',
  templateUrl: './introduce-article-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IntroduceArticleUpdateComponent implements OnInit {
  isSaving = false;
  introduceArticle: IIntroduceArticle | null = null;

  editForm: IntroduceArticleFormGroup = this.introduceArticleFormService.createIntroduceArticleFormGroup();

  constructor(
    protected introduceArticleService: IntroduceArticleService,
    protected introduceArticleFormService: IntroduceArticleFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ introduceArticle }) => {
      this.introduceArticle = introduceArticle;
      if (introduceArticle) {
        this.updateForm(introduceArticle);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const introduceArticle = this.introduceArticleFormService.getIntroduceArticle(this.editForm);
    if (introduceArticle.id !== null) {
      this.subscribeToSaveResponse(this.introduceArticleService.update(introduceArticle));
    } else {
      this.subscribeToSaveResponse(this.introduceArticleService.create(introduceArticle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntroduceArticle>>): void {
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

  protected updateForm(introduceArticle: IIntroduceArticle): void {
    this.introduceArticle = introduceArticle;
    this.introduceArticleFormService.resetForm(this.editForm, introduceArticle);
  }
}
