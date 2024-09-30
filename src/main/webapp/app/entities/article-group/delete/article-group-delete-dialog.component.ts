import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IArticleGroup } from '../article-group.model';
import { ArticleGroupService } from '../service/article-group.service';

@Component({
  standalone: true,
  templateUrl: './article-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ArticleGroupDeleteDialogComponent {
  articleGroup?: IArticleGroup;

  constructor(
    protected articleGroupService: ArticleGroupService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.articleGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
