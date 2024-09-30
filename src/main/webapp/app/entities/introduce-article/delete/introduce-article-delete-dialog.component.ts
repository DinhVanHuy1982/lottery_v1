import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIntroduceArticle } from '../introduce-article.model';
import { IntroduceArticleService } from '../service/introduce-article.service';

@Component({
  standalone: true,
  templateUrl: './introduce-article-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IntroduceArticleDeleteDialogComponent {
  introduceArticle?: IIntroduceArticle;

  constructor(
    protected introduceArticleService: IntroduceArticleService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.introduceArticleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
