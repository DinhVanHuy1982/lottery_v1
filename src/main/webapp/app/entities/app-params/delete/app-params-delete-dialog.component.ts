import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAppParams } from '../app-params.model';
import { AppParamsService } from '../service/app-params.service';

@Component({
  standalone: true,
  templateUrl: './app-params-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AppParamsDeleteDialogComponent {
  appParams?: IAppParams;

  constructor(
    protected appParamsService: AppParamsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appParamsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
