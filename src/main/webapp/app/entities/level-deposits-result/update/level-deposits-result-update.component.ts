import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ILevelDepositsResult } from '../level-deposits-result.model';
import { LevelDepositsResultService } from '../service/level-deposits-result.service';
import { LevelDepositsResultFormService, LevelDepositsResultFormGroup } from './level-deposits-result-form.service';

@Component({
  standalone: true,
  selector: 'jhi-level-deposits-result-update',
  templateUrl: './level-deposits-result-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LevelDepositsResultUpdateComponent implements OnInit {
  isSaving = false;
  levelDepositsResult: ILevelDepositsResult | null = null;

  editForm: LevelDepositsResultFormGroup = this.levelDepositsResultFormService.createLevelDepositsResultFormGroup();

  constructor(
    protected levelDepositsResultService: LevelDepositsResultService,
    protected levelDepositsResultFormService: LevelDepositsResultFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ levelDepositsResult }) => {
      this.levelDepositsResult = levelDepositsResult;
      if (levelDepositsResult) {
        this.updateForm(levelDepositsResult);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const levelDepositsResult = this.levelDepositsResultFormService.getLevelDepositsResult(this.editForm);
    if (levelDepositsResult.id !== null) {
      this.subscribeToSaveResponse(this.levelDepositsResultService.update(levelDepositsResult));
    } else {
      this.subscribeToSaveResponse(this.levelDepositsResultService.create(levelDepositsResult));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevelDepositsResult>>): void {
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

  protected updateForm(levelDepositsResult: ILevelDepositsResult): void {
    this.levelDepositsResult = levelDepositsResult;
    this.levelDepositsResultFormService.resetForm(this.editForm, levelDepositsResult);
  }
}
