import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIntroduceArticleGroup } from '../introduce-article-group.model';
import { IntroduceArticleGroupService } from '../service/introduce-article-group.service';

@Component({
  standalone: true,
  templateUrl: './introduce-article-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IntroduceArticleGroupDeleteDialogComponent {
  introduceArticleGroup?: IIntroduceArticleGroup;

  constructor(
    protected introduceArticleGroupService: IntroduceArticleGroupService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.introduceArticleGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
