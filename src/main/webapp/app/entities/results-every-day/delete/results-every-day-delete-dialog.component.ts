import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IResultsEveryDay } from '../results-every-day.model';
import { ResultsEveryDayService } from '../service/results-every-day.service';

@Component({
  standalone: true,
  templateUrl: './results-every-day-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ResultsEveryDayDeleteDialogComponent {
  resultsEveryDay?: IResultsEveryDay;

  constructor(
    protected resultsEveryDayService: ResultsEveryDayService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultsEveryDayService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
