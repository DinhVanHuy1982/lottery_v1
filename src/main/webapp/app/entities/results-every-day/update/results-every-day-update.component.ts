import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IResultsEveryDay } from '../results-every-day.model';
import { ResultsEveryDayService } from '../service/results-every-day.service';
import { ResultsEveryDayFormService, ResultsEveryDayFormGroup } from './results-every-day-form.service';

@Component({
  standalone: true,
  selector: 'jhi-results-every-day-update',
  templateUrl: './results-every-day-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ResultsEveryDayUpdateComponent implements OnInit {
  isSaving = false;
  resultsEveryDay: IResultsEveryDay | null = null;

  editForm: ResultsEveryDayFormGroup = this.resultsEveryDayFormService.createResultsEveryDayFormGroup();

  constructor(
    protected resultsEveryDayService: ResultsEveryDayService,
    protected resultsEveryDayFormService: ResultsEveryDayFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultsEveryDay }) => {
      this.resultsEveryDay = resultsEveryDay;
      if (resultsEveryDay) {
        this.updateForm(resultsEveryDay);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultsEveryDay = this.resultsEveryDayFormService.getResultsEveryDay(this.editForm);
    if (resultsEveryDay.id !== null) {
      this.subscribeToSaveResponse(this.resultsEveryDayService.update(resultsEveryDay));
    } else {
      this.subscribeToSaveResponse(this.resultsEveryDayService.create(resultsEveryDay));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultsEveryDay>>): void {
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

  protected updateForm(resultsEveryDay: IResultsEveryDay): void {
    this.resultsEveryDay = resultsEveryDay;
    this.resultsEveryDayFormService.resetForm(this.editForm, resultsEveryDay);
  }
}
