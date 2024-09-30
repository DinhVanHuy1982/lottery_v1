import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRoleFunctionAction } from '../role-function-action.model';
import { RoleFunctionActionService } from '../service/role-function-action.service';

@Component({
  standalone: true,
  templateUrl: './role-function-action-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RoleFunctionActionDeleteDialogComponent {
  roleFunctionAction?: IRoleFunctionAction;

  constructor(
    protected roleFunctionActionService: RoleFunctionActionService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.roleFunctionActionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
