import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRandomResults } from '../random-results.model';
import { RandomResultsService } from '../service/random-results.service';
import { RandomResultsFormService, RandomResultsFormGroup } from './random-results-form.service';

@Component({
  standalone: true,
  selector: 'jhi-random-results-update',
  templateUrl: './random-results-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RandomResultsUpdateComponent implements OnInit {
  isSaving = false;
  randomResults: IRandomResults | null = null;

  editForm: RandomResultsFormGroup = this.randomResultsFormService.createRandomResultsFormGroup();

  constructor(
    protected randomResultsService: RandomResultsService,
    protected randomResultsFormService: RandomResultsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ randomResults }) => {
      this.randomResults = randomResults;
      if (randomResults) {
        this.updateForm(randomResults);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const randomResults = this.randomResultsFormService.getRandomResults(this.editForm);
    if (randomResults.id !== null) {
      this.subscribeToSaveResponse(this.randomResultsService.update(randomResults));
    } else {
      this.subscribeToSaveResponse(this.randomResultsService.create(randomResults));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRandomResults>>): void {
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

  protected updateForm(randomResults: IRandomResults): void {
    this.randomResults = randomResults;
    this.randomResultsFormService.resetForm(this.editForm, randomResults);
  }
}
