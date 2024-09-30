import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRoleFunctions } from '../role-functions.model';
import { RoleFunctionsService } from '../service/role-functions.service';

@Component({
  standalone: true,
  templateUrl: './role-functions-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RoleFunctionsDeleteDialogComponent {
  roleFunctions?: IRoleFunctions;

  constructor(
    protected roleFunctionsService: RoleFunctionsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.roleFunctionsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
