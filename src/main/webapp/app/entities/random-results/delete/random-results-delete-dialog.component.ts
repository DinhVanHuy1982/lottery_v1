import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRandomResults } from '../random-results.model';
import { RandomResultsService } from '../service/random-results.service';

@Component({
  standalone: true,
  templateUrl: './random-results-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RandomResultsDeleteDialogComponent {
  randomResults?: IRandomResults;

  constructor(
    protected randomResultsService: RandomResultsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.randomResultsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
