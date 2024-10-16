import { ChangeDetectorRef, Component, ElementRef, Inject, OnInit } from '@angular/core';
import { AgGridAngular } from 'ag-grid-angular';
import { NgForOf, NgIf } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { NzTimePickerComponent } from 'ng-zorro-antd/time-picker';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ArticleGroupService } from '../../../../article-group/service/article-group.service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FileDetailComponent } from '../../../../../shared/common/file-detail/file-detail.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  selector: 'jhi-add-level-deposits',
  standalone: true,
  imports: [AgGridAngular, NgIf, NgSelectModule, NzTimePickerComponent, ReactiveFormsModule, NgForOf, CKEditorModule],
  templateUrl: './add-level-deposits.component.html',
  styleUrl: './add-level-deposits.component.scss',
})
export class AddLevelDepositsComponent implements OnInit {
  formIntroduceArticle: FormGroup;

  // file upload
  lstUrlImgUpdate: any;
  listPathImg: any; // chứa danh sách các product_img
  productImgDelete: any[] = [];
  lstAllUrlExpand: any[] = [];
  domainFile = '';

  // ck editor
  public Editor = ClassicEditor;
  config = {
    toolbar: {
      item: [
        'heading',
        'fontFamily',
        'fontColor',
        '|',
        'bold',
        'italic',
        'strikethrough',
        'underline',
        '|',
        'subscript',
        'superscript',
        'link',
        '|',
        'bulletedList',
        'numberedList',
        '|',
        'alignment:left',
        'alignment:right',
        'alignment:center',
        'alignment:justify',
        '|',
        'uploadImage',
        'blockQuote',
        'insertTable',
        '|',
        'undo',
        'redo',
        'outdent',
        'indent',
      ],
      shouldNotGroupWhenFull: true,
    },
    isReadOnly: true,
    allowedContent: true,
    extraAllowedContent: 'span;strong;ul;li;table;td;style;*[id];*(*);*{*}',
    characterCount: true,
  };

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AddLevelDepositsComponent>,
    private translate: TranslateService,
    private toast: ToastrService,
    private cdr: ChangeDetectorRef,
    private dialog: MatDialog,
    private elementRef: ElementRef,
    private fb: FormBuilder,
  ) {
    this.formIntroduceArticle = this.fb.group({
      id: new FormControl(null, { validators: [] }),
      title: new FormControl('', { validators: [Validators.required, Validators.maxLength(500)] }),
      name: new FormControl('', { validators: [Validators.required, Validators.maxLength(50)] }),
      content: new FormControl('', { validators: [Validators.required, Validators.maxLength(2000)] }),
    });
  }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  handleDeleteFake() {
    const element = document.querySelector('.fakeTab-ShiftTab') as HTMLElement;
    if (element) {
      element.remove(); // Xóa phần tử khỏi DOM
    }
  }

  close() {
    this.dialogRef.close();
  }

  submit() {
    console.log('Value form : ', this.formIntroduceArticle?.value);
  }

  imageUrls: string[] = [];
  lstFileProduct: any[] = [];
  onFileSelected(event: any) {
    const files = event.target.files;
    this.lstFileProduct = files;
    console.log('File upload: ', this.lstFileProduct);
    if (files) {
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = () => {
          this.imageUrls.push(reader.result as string);
        };
        reader.readAsDataURL(files[i]);
      }
    }
  }

  removeImgProduct(urlImg: any, index: any, isUpdate: boolean) {
    if (isUpdate) {
      if (this.listPathImg) {
        this.lstUrlImgUpdate = this.lstUrlImgUpdate.filter((item: any) => item != urlImg);
        for (let item of this.listPathImg) {
          if (item.fileName === urlImg) {
            this.productImgDelete.push(item.id);
          }
        }
      }
    } else {
      this.imageUrls = this.imageUrls.filter((item: any) => item != urlImg);
      if (index >= 0 && index < this.lstFileProduct.length) {
        const filesArray: File[] = Array.from(this.lstFileProduct);
        filesArray.splice(index, 1);
        this.lstFileProduct = filesArray;
        console.log(this.lstFileProduct);
      }
    }
  }

  expandImg(index: number, isFileSerVer: boolean) {
    this.lstAllUrlExpand = [];
    if (this.lstUrlImgUpdate?.length > 0) {
      this.lstAllUrlExpand = this.lstUrlImgUpdate.map((item: any) => {
        return this.domainFile + item;
      });
    }
    if (this.imageUrls?.length > 0) {
      this.lstAllUrlExpand = [...this.lstAllUrlExpand, ...this.imageUrls];
    }

    let indexFile = 0;
    if (isFileSerVer) {
      indexFile = index;
    } else {
      if (this.lstUrlImgUpdate) {
        indexFile = this.lstUrlImgUpdate?.length + indexFile + 1;
      } else {
        indexFile = indexFile + 1;
      }
    }

    const data = { lstFile: this.lstAllUrlExpand, index: indexFile };

    this.dialog
      .open(FileDetailComponent, {
        data,
        disableClose: false,
        hasBackdrop: true,
        panelClass: 'overflow-hidden-cus',
        width: '860px',
        height: '860px',
        maxWidth: '90vw',
        maxHeight: '90vh',
      })
      .afterClosed();
  }
  changeMailContent() {}
}
