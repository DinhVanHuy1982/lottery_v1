import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPrizes } from '../prizes.model';
import { PrizesService } from '../service/prizes.service';
import { PrizesFormService, PrizesFormGroup } from './prizes-form.service';

@Component({
  standalone: true,
  selector: 'jhi-prizes-update',
  templateUrl: './prizes-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PrizesUpdateComponent implements OnInit {
  isSaving = false;
  prizes: IPrizes | null = null;

  editForm: PrizesFormGroup = this.prizesFormService.createPrizesFormGroup();

  constructor(
    protected prizesService: PrizesService,
    protected prizesFormService: PrizesFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prizes }) => {
      this.prizes = prizes;
      if (prizes) {
        this.updateForm(prizes);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prizes = this.prizesFormService.getPrizes(this.editForm);
    if (prizes.id !== null) {
      this.subscribeToSaveResponse(this.prizesService.update(prizes));
    } else {
      this.subscribeToSaveResponse(this.prizesService.create(prizes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrizes>>): void {
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

  protected updateForm(prizes: IPrizes): void {
    this.prizes = prizes;
    this.prizesFormService.resetForm(this.editForm, prizes);
  }
}
