import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFunctions } from '../functions.model';
import { FunctionsService } from '../service/functions.service';

@Component({
  standalone: true,
  templateUrl: './functions-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FunctionsDeleteDialogComponent {
  functions?: IFunctions;

  constructor(
    protected functionsService: FunctionsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.functionsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
