import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRoleFunctions } from '../role-functions.model';
import { RoleFunctionsService } from '../service/role-functions.service';
import { RoleFunctionsFormService, RoleFunctionsFormGroup } from './role-functions-form.service';

@Component({
  standalone: true,
  selector: 'jhi-role-functions-update',
  templateUrl: './role-functions-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RoleFunctionsUpdateComponent implements OnInit {
  isSaving = false;
  roleFunctions: IRoleFunctions | null = null;

  editForm: RoleFunctionsFormGroup = this.roleFunctionsFormService.createRoleFunctionsFormGroup();

  constructor(
    protected roleFunctionsService: RoleFunctionsService,
    protected roleFunctionsFormService: RoleFunctionsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleFunctions }) => {
      this.roleFunctions = roleFunctions;
      if (roleFunctions) {
        this.updateForm(roleFunctions);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleFunctions = this.roleFunctionsFormService.getRoleFunctions(this.editForm);
    if (roleFunctions.id !== null) {
      this.subscribeToSaveResponse(this.roleFunctionsService.update(roleFunctions));
    } else {
      this.subscribeToSaveResponse(this.roleFunctionsService.create(roleFunctions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleFunctions>>): void {
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

  protected updateForm(roleFunctions: IRoleFunctions): void {
    this.roleFunctions = roleFunctions;
    this.roleFunctionsFormService.resetForm(this.editForm, roleFunctions);
  }
}
