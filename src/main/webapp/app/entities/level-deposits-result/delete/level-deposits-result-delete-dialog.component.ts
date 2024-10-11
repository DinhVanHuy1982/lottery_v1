import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILevelDepositsResult } from '../level-deposits-result.model';
import { LevelDepositsResultService } from '../service/level-deposits-result.service';

@Component({
  standalone: true,
  templateUrl: './level-deposits-result-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LevelDepositsResultDeleteDialogComponent {
  levelDepositsResult?: ILevelDepositsResult;

  constructor(
    protected levelDepositsResultService: LevelDepositsResultService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelDepositsResultService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
