import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IActions } from '../actions.model';
import { ActionsService } from '../service/actions.service';
import { ActionsFormService, ActionsFormGroup } from './actions-form.service';

@Component({
  standalone: true,
  selector: 'jhi-actions-update',
  templateUrl: './actions-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ActionsUpdateComponent implements OnInit {
  isSaving = false;
  actions: IActions | null = null;

  editForm: ActionsFormGroup = this.actionsFormService.createActionsFormGroup();

  constructor(
    protected actionsService: ActionsService,
    protected actionsFormService: ActionsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actions }) => {
      this.actions = actions;
      if (actions) {
        this.updateForm(actions);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actions = this.actionsFormService.getActions(this.editForm);
    if (actions.id !== null) {
      this.subscribeToSaveResponse(this.actionsService.update(actions));
    } else {
      this.subscribeToSaveResponse(this.actionsService.create(actions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActions>>): void {
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

  protected updateForm(actions: IActions): void {
    this.actions = actions;
    this.actionsFormService.resetForm(this.editForm, actions);
  }
}
