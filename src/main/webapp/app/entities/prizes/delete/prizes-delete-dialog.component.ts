import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPrizes } from '../prizes.model';
import { PrizesService } from '../service/prizes.service';

@Component({
  standalone: true,
  templateUrl: './prizes-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PrizesDeleteDialogComponent {
  prizes?: IPrizes;

  constructor(
    protected prizesService: PrizesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prizesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
