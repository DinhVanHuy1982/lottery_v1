import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ILevelDeposits } from '../level-deposits.model';
import { LevelDepositsService } from '../service/level-deposits.service';
import { LevelDepositsFormService, LevelDepositsFormGroup } from './level-deposits-form.service';

@Component({
  standalone: true,
  selector: 'jhi-level-deposits-update',
  templateUrl: './level-deposits-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LevelDepositsUpdateComponent implements OnInit {
  isSaving = false;
  levelDeposits: ILevelDeposits | null = null;

  editForm: LevelDepositsFormGroup = this.levelDepositsFormService.createLevelDepositsFormGroup();

  constructor(
    protected levelDepositsService: LevelDepositsService,
    protected levelDepositsFormService: LevelDepositsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ levelDeposits }) => {
      this.levelDeposits = levelDeposits;
      if (levelDeposits) {
        this.updateForm(levelDeposits);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const levelDeposits = this.levelDepositsFormService.getLevelDeposits(this.editForm);
    if (levelDeposits.id !== null) {
      this.subscribeToSaveResponse(this.levelDepositsService.update(levelDeposits));
    } else {
      this.subscribeToSaveResponse(this.levelDepositsService.create(levelDeposits));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevelDeposits>>): void {
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

  protected updateForm(levelDeposits: ILevelDeposits): void {
    this.levelDeposits = levelDeposits;
    this.levelDepositsFormService.resetForm(this.editForm, levelDeposits);
  }
}
