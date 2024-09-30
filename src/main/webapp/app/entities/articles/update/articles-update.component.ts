import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IArticles } from '../articles.model';
import { ArticlesService } from '../service/articles.service';
import { ArticlesFormService, ArticlesFormGroup } from './articles-form.service';

@Component({
  standalone: true,
  selector: 'jhi-articles-update',
  templateUrl: './articles-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ArticlesUpdateComponent implements OnInit {
  isSaving = false;
  articles: IArticles | null = null;

  editForm: ArticlesFormGroup = this.articlesFormService.createArticlesFormGroup();

  constructor(
    protected articlesService: ArticlesService,
    protected articlesFormService: ArticlesFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articles }) => {
      this.articles = articles;
      if (articles) {
        this.updateForm(articles);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const articles = this.articlesFormService.getArticles(this.editForm);
    if (articles.id !== null) {
      this.subscribeToSaveResponse(this.articlesService.update(articles));
    } else {
      this.subscribeToSaveResponse(this.articlesService.create(articles));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticles>>): void {
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

  protected updateForm(articles: IArticles): void {
    this.articles = articles;
    this.articlesFormService.resetForm(this.editForm, articles);
  }
}
