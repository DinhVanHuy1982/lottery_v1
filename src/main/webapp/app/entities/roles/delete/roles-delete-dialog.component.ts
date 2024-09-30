import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRoles } from '../roles.model';
import { RolesService } from '../service/roles.service';

@Component({
  standalone: true,
  templateUrl: './roles-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RolesDeleteDialogComponent {
  roles?: IRoles;

  constructor(
    protected rolesService: RolesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rolesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
