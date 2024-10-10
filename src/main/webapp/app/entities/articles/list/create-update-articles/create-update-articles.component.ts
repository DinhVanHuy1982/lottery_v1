import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { NgClass, NgIf } from '@angular/common';
import { NzTimePickerModule } from 'ng-zorro-antd/time-picker';
import { NgSelectModule } from '@ng-select/ng-select';
import { ArticleGroupService } from '../../../article-group/service/article-group.service';

@Component({
  selector: 'jhi-create-update-articles',
  standalone: true,
  imports: [NgIf, NgClass, NzTimePickerModule, NgSelectModule],
  templateUrl: './create-update-articles.component.html',
  styleUrl: './create-update-articles.component.scss',
})
export class CreateUpdateArticlesComponent implements OnInit, OnDestroy, AfterViewInit {
  isLoading = false;
  listArticleGroup: any[] = [];
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<CreateUpdateArticlesComponent>,
    private translate: TranslateService,
    private toast: ToastrService,
    private cdr: ChangeDetectorRef,
    private dialog: MatDialog,
    private elementRef: ElementRef,
    private articleGroupService: ArticleGroupService,
  ) {}
  ngOnDestroy(): void {}

  ngOnInit(): void {
    this.getListCodeAndNameGroup();
  }

  getListCodeAndNameGroup() {
    this.articleGroupService.getLstArticleGroupCodeAndName().subscribe(res => {
      if (res.status === '00') {
        this.listArticleGroup = res.data;
      } else {
        this.toast.error(res.message);
      }
    });
  }
  close() {
    this.dialogRef.close();
  }

  submit() {}

  upload(file: any) {}

  addDocument() {}

  importDocument() {}

  getListDocumentInProfileCodeAndTextIdentifier() {}
  initColumnDef() {}

  ngAfterViewInit(): void {}

  handleDeleteFake() {
    const element = document.querySelector('.fakeTab-ShiftTab') as HTMLElement;
    if (element) {
      element.remove(); // Xóa phần tử khỏi DOM
    }
  }
}
