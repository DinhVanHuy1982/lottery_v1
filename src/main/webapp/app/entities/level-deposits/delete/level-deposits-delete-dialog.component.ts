import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILevelDeposits } from '../level-deposits.model';
import { LevelDepositsService } from '../service/level-deposits.service';

@Component({
  standalone: true,
  templateUrl: './level-deposits-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LevelDepositsDeleteDialogComponent {
  levelDeposits?: ILevelDeposits;

  constructor(
    protected levelDepositsService: LevelDepositsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelDepositsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
