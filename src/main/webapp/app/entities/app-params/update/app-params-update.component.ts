import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAppParams } from '../app-params.model';
import { AppParamsService } from '../service/app-params.service';
import { AppParamsFormService, AppParamsFormGroup } from './app-params-form.service';

@Component({
  standalone: true,
  selector: 'jhi-app-params-update',
  templateUrl: './app-params-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AppParamsUpdateComponent implements OnInit {
  isSaving = false;
  appParams: IAppParams | null = null;

  editForm: AppParamsFormGroup = this.appParamsFormService.createAppParamsFormGroup();

  constructor(
    protected appParamsService: AppParamsService,
    protected appParamsFormService: AppParamsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appParams }) => {
      this.appParams = appParams;
      if (appParams) {
        this.updateForm(appParams);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appParams = this.appParamsFormService.getAppParams(this.editForm);
    if (appParams.id !== null) {
      this.subscribeToSaveResponse(this.appParamsService.update(appParams));
    } else {
      this.subscribeToSaveResponse(this.appParamsService.create(appParams));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppParams>>): void {
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

  protected updateForm(appParams: IAppParams): void {
    this.appParams = appParams;
    this.appParamsFormService.resetForm(this.editForm, appParams);
  }
}
