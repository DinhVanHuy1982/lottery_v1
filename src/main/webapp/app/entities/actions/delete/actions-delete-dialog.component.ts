import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IActions } from '../actions.model';
import { ActionsService } from '../service/actions.service';

@Component({
  standalone: true,
  templateUrl: './actions-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ActionsDeleteDialogComponent {
  actions?: IActions;

  constructor(
    protected actionsService: ActionsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actionsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
