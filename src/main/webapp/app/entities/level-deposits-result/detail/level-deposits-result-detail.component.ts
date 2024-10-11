import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ILevelDepositsResult } from '../level-deposits-result.model';

@Component({
  standalone: true,
  selector: 'jhi-level-deposits-result-detail',
  templateUrl: './level-deposits-result-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class LevelDepositsResultDetailComponent {
  @Input() levelDepositsResult: ILevelDepositsResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
