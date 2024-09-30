import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDeposits } from '../deposits.model';
import { DepositsService } from '../service/deposits.service';
import { DepositsFormService, DepositsFormGroup } from './deposits-form.service';

@Component({
  standalone: true,
  selector: 'jhi-deposits-update',
  templateUrl: './deposits-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepositsUpdateComponent implements OnInit {
  isSaving = false;
  deposits: IDeposits | null = null;

  editForm: DepositsFormGroup = this.depositsFormService.createDepositsFormGroup();

  constructor(
    protected depositsService: DepositsService,
    protected depositsFormService: DepositsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deposits }) => {
      this.deposits = deposits;
      if (deposits) {
        this.updateForm(deposits);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deposits = this.depositsFormService.getDeposits(this.editForm);
    if (deposits.id !== null) {
      this.subscribeToSaveResponse(this.depositsService.update(deposits));
    } else {
      this.subscribeToSaveResponse(this.depositsService.create(deposits));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeposits>>): void {
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

  protected updateForm(deposits: IDeposits): void {
    this.deposits = deposits;
    this.depositsFormService.resetForm(this.editForm, deposits);
  }
}
