import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { NgClass, NgIf } from '@angular/common';
import { NzTimePickerModule } from 'ng-zorro-antd/time-picker';

@Component({
  selector: 'jhi-create-update-articles',
  standalone: true,
  imports: [NgIf, NgClass, NzTimePickerModule],
  templateUrl: './create-update-articles.component.html',
  styleUrl: './create-update-articles.component.scss',
})
export class CreateUpdateArticlesComponent implements OnInit, OnDestroy, AfterViewInit {
  isLoading = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<CreateUpdateArticlesComponent>,
    private translate: TranslateService,
    private toast: ToastrService,
    private cdr: ChangeDetectorRef,
    private dialog: MatDialog,
    private elementRef: ElementRef,
  ) {}
  ngOnDestroy(): void {}

  ngOnInit(): void {}

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
