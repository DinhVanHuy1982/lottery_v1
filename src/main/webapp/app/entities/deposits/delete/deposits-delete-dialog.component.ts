import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDeposits } from '../deposits.model';
import { DepositsService } from '../service/deposits.service';

@Component({
  standalone: true,
  templateUrl: './deposits-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DepositsDeleteDialogComponent {
  deposits?: IDeposits;

  constructor(
    protected depositsService: DepositsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.depositsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
