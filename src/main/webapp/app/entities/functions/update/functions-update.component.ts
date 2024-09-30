import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFunctions } from '../functions.model';
import { FunctionsService } from '../service/functions.service';
import { FunctionsFormService, FunctionsFormGroup } from './functions-form.service';

@Component({
  standalone: true,
  selector: 'jhi-functions-update',
  templateUrl: './functions-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FunctionsUpdateComponent implements OnInit {
  isSaving = false;
  functions: IFunctions | null = null;

  editForm: FunctionsFormGroup = this.functionsFormService.createFunctionsFormGroup();

  constructor(
    protected functionsService: FunctionsService,
    protected functionsFormService: FunctionsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ functions }) => {
      this.functions = functions;
      if (functions) {
        this.updateForm(functions);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const functions = this.functionsFormService.getFunctions(this.editForm);
    if (functions.id !== null) {
      this.subscribeToSaveResponse(this.functionsService.update(functions));
    } else {
      this.subscribeToSaveResponse(this.functionsService.create(functions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFunctions>>): void {
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

  protected updateForm(functions: IFunctions): void {
    this.functions = functions;
    this.functionsFormService.resetForm(this.editForm, functions);
  }
}
