import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFileSaves } from '../file-saves.model';
import { FileSavesService } from '../service/file-saves.service';

@Component({
  standalone: true,
  templateUrl: './file-saves-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FileSavesDeleteDialogComponent {
  fileSaves?: IFileSaves;

  constructor(
    protected fileSavesService: FileSavesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fileSavesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
