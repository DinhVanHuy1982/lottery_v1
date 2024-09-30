import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFileSaves } from '../file-saves.model';
import { FileSavesService } from '../service/file-saves.service';
import { FileSavesFormService, FileSavesFormGroup } from './file-saves-form.service';

@Component({
  standalone: true,
  selector: 'jhi-file-saves-update',
  templateUrl: './file-saves-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FileSavesUpdateComponent implements OnInit {
  isSaving = false;
  fileSaves: IFileSaves | null = null;

  editForm: FileSavesFormGroup = this.fileSavesFormService.createFileSavesFormGroup();

  constructor(
    protected fileSavesService: FileSavesService,
    protected fileSavesFormService: FileSavesFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileSaves }) => {
      this.fileSaves = fileSaves;
      if (fileSaves) {
        this.updateForm(fileSaves);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fileSaves = this.fileSavesFormService.getFileSaves(this.editForm);
    if (fileSaves.id !== null) {
      this.subscribeToSaveResponse(this.fileSavesService.update(fileSaves));
    } else {
      this.subscribeToSaveResponse(this.fileSavesService.create(fileSaves));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileSaves>>): void {
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

  protected updateForm(fileSaves: IFileSaves): void {
    this.fileSaves = fileSaves;
    this.fileSavesFormService.resetForm(this.editForm, fileSaves);
  }
}
