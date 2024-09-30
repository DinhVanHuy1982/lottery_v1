import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRoleFunctionAction } from '../role-function-action.model';
import { RoleFunctionActionService } from '../service/role-function-action.service';
import { RoleFunctionActionFormService, RoleFunctionActionFormGroup } from './role-function-action-form.service';

@Component({
  standalone: true,
  selector: 'jhi-role-function-action-update',
  templateUrl: './role-function-action-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RoleFunctionActionUpdateComponent implements OnInit {
  isSaving = false;
  roleFunctionAction: IRoleFunctionAction | null = null;

  editForm: RoleFunctionActionFormGroup = this.roleFunctionActionFormService.createRoleFunctionActionFormGroup();

  constructor(
    protected roleFunctionActionService: RoleFunctionActionService,
    protected roleFunctionActionFormService: RoleFunctionActionFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleFunctionAction }) => {
      this.roleFunctionAction = roleFunctionAction;
      if (roleFunctionAction) {
        this.updateForm(roleFunctionAction);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleFunctionAction = this.roleFunctionActionFormService.getRoleFunctionAction(this.editForm);
    if (roleFunctionAction.id !== null) {
      this.subscribeToSaveResponse(this.roleFunctionActionService.update(roleFunctionAction));
    } else {
      this.subscribeToSaveResponse(this.roleFunctionActionService.create(roleFunctionAction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleFunctionAction>>): void {
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

  protected updateForm(roleFunctionAction: IRoleFunctionAction): void {
    this.roleFunctionAction = roleFunctionAction;
    this.roleFunctionActionFormService.resetForm(this.editForm, roleFunctionAction);
  }
}
