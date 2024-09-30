import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IIntroduceArticleGroup } from '../introduce-article-group.model';

@Component({
  standalone: true,
  selector: 'jhi-introduce-article-group-detail',
  templateUrl: './introduce-article-group-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class IntroduceArticleGroupDetailComponent {
  @Input() introduceArticleGroup: IIntroduceArticleGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
