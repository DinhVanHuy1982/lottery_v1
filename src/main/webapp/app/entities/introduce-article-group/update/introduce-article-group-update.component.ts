import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IIntroduceArticleGroup } from '../introduce-article-group.model';
import { IntroduceArticleGroupService } from '../service/introduce-article-group.service';
import { IntroduceArticleGroupFormService, IntroduceArticleGroupFormGroup } from './introduce-article-group-form.service';

@Component({
  standalone: true,
  selector: 'jhi-introduce-article-group-update',
  templateUrl: './introduce-article-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IntroduceArticleGroupUpdateComponent implements OnInit {
  isSaving = false;
  introduceArticleGroup: IIntroduceArticleGroup | null = null;

  editForm: IntroduceArticleGroupFormGroup = this.introduceArticleGroupFormService.createIntroduceArticleGroupFormGroup();

  constructor(
    protected introduceArticleGroupService: IntroduceArticleGroupService,
    protected introduceArticleGroupFormService: IntroduceArticleGroupFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ introduceArticleGroup }) => {
      this.introduceArticleGroup = introduceArticleGroup;
      if (introduceArticleGroup) {
        this.updateForm(introduceArticleGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const introduceArticleGroup = this.introduceArticleGroupFormService.getIntroduceArticleGroup(this.editForm);
    if (introduceArticleGroup.id !== null) {
      this.subscribeToSaveResponse(this.introduceArticleGroupService.update(introduceArticleGroup));
    } else {
      this.subscribeToSaveResponse(this.introduceArticleGroupService.create(introduceArticleGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntroduceArticleGroup>>): void {
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

  protected updateForm(introduceArticleGroup: IIntroduceArticleGroup): void {
    this.introduceArticleGroup = introduceArticleGroup;
    this.introduceArticleGroupFormService.resetForm(this.editForm, introduceArticleGroup);
  }
}
