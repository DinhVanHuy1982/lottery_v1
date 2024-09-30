import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IIntroduceArticle } from '../introduce-article.model';

@Component({
  standalone: true,
  selector: 'jhi-introduce-article-detail',
  templateUrl: './introduce-article-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class IntroduceArticleDetailComponent {
  @Input() introduceArticle: IIntroduceArticle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
