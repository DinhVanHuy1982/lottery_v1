import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { NgClass, NgForOf, NgIf } from '@angular/common';
import { NzTimePickerModule } from 'ng-zorro-antd/time-picker';
import { NgSelectModule } from '@ng-select/ng-select';
import { ArticleGroupService } from '../../../article-group/service/article-group.service';
import { AgGridAngular } from 'ag-grid-angular';
import { AddLevelDepositsComponent } from './add-level-deposits/add-level-deposits.component';
import { FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { ITEMS_PER_PAGE } from '../../../../config/pagination.constants';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { ColDef } from 'ag-grid-enterprise';
import { TYPE_MESSAGE } from '../../../../service/utils/constants';
import { MessagePipe } from '../../../../shared/message.pipe';
// import {validations} from "generator-jhipster/dist/types/jdl";
// import {CommonFunction, validDateValidator} from "../../../../service/utils/common-function";

@Component({
  selector: 'jhi-create-update-articles',
  standalone: true,
  imports: [
    NgIf,
    NgClass,
    NzTimePickerModule,
    NgSelectModule,
    AgGridAngular,
    NgForOf,
    ReactiveFormsModule,
    FormsModule,
    MatTooltipModule,
    CKEditorModule,
    MessagePipe,
  ],
  templateUrl: './create-update-articles.component.html',
  styleUrl: './create-update-articles.component.scss',
})
export class CreateUpdateArticlesComponent implements OnInit, OnDestroy, AfterViewInit {
  isLoading = false;
  listArticleGroup: any[] = [];
  rowHeight = 40;
  columnDefs: any[] = [];
  rowData: any[] = [];
  lstLevelDeposits: any[] = [];
  lstGroupArticle: any[] = [];
  totalIntroduce = 0;
  page = 1;
  itemsPerPage = ITEMS_PER_PAGE;
  TYPE_MESSAGE = TYPE_MESSAGE;

  formArticle: FormGroup;
  startTime: any;
  endTime: any;

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
    public dialogRef: MatDialogRef<CreateUpdateArticlesComponent>,
    private translate: TranslateService,
    private toast: ToastrService,
    private cdr: ChangeDetectorRef,
    private dialog: MatDialog,
    private elementRef: ElementRef,
    private articleGroupService: ArticleGroupService,
    private fb: FormBuilder,
  ) {
    this.formArticle = this.fb.group({
      name: new FormControl('', { validators: [Validators.required, Validators.maxLength(50)] }),
      title: new FormControl('', { validators: [Validators.required, Validators.maxLength(500)] }),
      numberPrize: new FormControl(null, { validators: [Validators.required, Validators.maxLength(5), Validators.pattern(/^\d+$/)] }),
      articleGroupCode: new FormControl(null, { validators: [Validators.required] }),
      numberChoice: new FormControl(null, { validators: [Validators.required, Validators.maxLength(5), Validators.pattern(/^\d+$/)] }),
      numberOfDigits: new FormControl(null, { validators: [Validators.required, Validators.maxLength(5), Validators.pattern(/^\d+$/)] }),
      timeStart: new FormControl('', { validators: [Validators.required, Validators.pattern(/^(?:[01]\d|2[0-3]):[0-5]\d$/)] }),
      timeEnd: new FormControl('', { validators: [Validators.required, Validators.pattern(/^(?:[01]\d|2[0-3]):[0-5]\d$/)] }),
      content: new FormControl(null, { validators: [Validators.maxLength(2000)] }),
      levelDeposits: this.fb.array([]),
    });
  }
  ngOnDestroy(): void {}

  ngOnInit(): void {
    this.initColumnDef();
    this.getLstGroupArticle();
  }

  // Truy cập FormArray
  get levelDeposits(): FormArray {
    return this.formArticle.get('levelDeposits') as FormArray;
  }

  // Thêm FormGroup mới vào FormArray
  addItem(): void {
    const newItem = this.fb.group({
      name: ['', Validators.required],
      minPrice: ['', { validators: [Validators.required, Validators.pattern(/^\d+$/)] }],
      description: ['', Validators.required],
    });

    this.levelDeposits.push(newItem);
  }

  // Xóa một FormGroup khỏi FormArray
  removeItem(index: number): void {
    this.levelDeposits.removeAt(index);
  }

  onSubmit(): void {
    this.markAllAsTouchedAndDirty(this.formArticle);
    const data = {
      ...this.formArticle.value,
      lstIntroduce: [...this.rowData],
    };
    console.log(data);
  }

  markAllAsTouchedAndDirty(formGroupOrFormArray: FormGroup | FormArray) {
    Object.keys(formGroupOrFormArray.controls).forEach(field => {
      const control = formGroupOrFormArray.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
        control.markAsDirty({ onlySelf: true });
      } else if (control instanceof FormGroup || control instanceof FormArray) {
        this.markAllAsTouchedAndDirty(control);
      }
    });
  }

  cellStyle = {
    'align-content': 'center',
    'font-weight': '400',
    'font-size': '13px',
    'align-items': 'center',
    color: '#151515',
    top: '0px',
    'white-space': 'nowrap',
    'text-overflow': 'ellipsis',
    overflow: 'hidden',
    padding: '0 8px',
    'border-right': 'none',
  };
  getListDocumentInProfileCodeAndTextIdentifier() {}
  initColumnDef() {
    this.columnDefs.push(
      {
        headerName: 'STT',
        headerTooltip: 'STT',
        field: 'make',
        minWidth: 44,
        maxWidth: 44,
        sortable: false,
        valueGetter: (param: any) => {
          return param.node.rowIndex + ((this.page - 1) * this.itemsPerPage + 1);
        },
        headerClass: 'center gridTitle columnNo unPadding',
        suppressMovable: true,
        cellStyle: {
          'font-style': 'normal',
          'font-size': '13px',
          'line-height': '20px',
          color: '#151515',
          'font-weight': '400',
          'font-family': 'Inter',
          overflow: 'hidden',
          'text-overflow': 'ellipsis',
          'white-space': 'nowrap',
          display: 'flex',
          'align-items': 'center',
          'justify-content': 'center',
        },
      },
      {
        headerName: 'TÊN BÀI VIẾT',
        headerTooltip: 'TÊN BÀI VIẾT',
        field: 'name',
        minWidth: 150,
        maxWidth: 200,
        sortable: false,
        tooltipField: 'name',
        headerClass: 'gridTitle',
        suppressMovable: true,
        cellStyle: this.cellStyle,
      },
      {
        headerName: 'TIÊU ĐỀ BÀI VIẾT',
        headerTooltip: 'TIÊU ĐỀ BÀI VIẾT',
        field: 'title',
        minWidth: 170,
        maxWidth: 230,
        sortable: false,
        headerClass: 'gridTitle',
        tooltipField: 'title',
        suppressMovable: true,
        cellStyle: this.cellStyle,
      },
      {
        headerName: 'NỘI DUNG BÀI VIẾT',
        headerTooltip: 'NỘI DUNG BÀI VIẾT',
        field: 'content',
        minWidth: 300,
        sortable: false,
        cellRenderer: (params: any) => {
          return params.data.content;
        },
        headerClass: 'gridTitle',
        suppressMovable: true,
        cellStyle: this.cellStyle,
      },
    );
  }

  ngAfterViewInit(): void {}

  handleDeleteFake() {
    const element = document.querySelector('.fakeTab-ShiftTab') as HTMLElement;
    if (element) {
      element.remove(); // Xóa phần tử khỏi DOM
    }
  }

  gridSizeChanged($event: any) {}

  onGridReady($event: any) {}

  addConfigLottery() {
    this.dialog
      .open(AddLevelDepositsComponent, {
        data: { action: 'CR' },
        disableClose: true,
        hasBackdrop: true,
        width: '700px',
        height: '90vh',
        autoFocus: false,
        panelClass: 'rental-info',
      })
      .afterClosed()
      .subscribe((res: any) => {
        if (res.status === '00') {
          this.rowData.unshift(res.data);
          this.rowData = [...this.rowData];
          this.toast.success('Thêm mới bài giới thiệu thành công');
        }
      });
  }
  // get subForms(): FormArray {
  //   return this.formArticle.get('levelDeposit') as FormArray;
  // }

  submit() {
    const dataCreateUpdate = {
      ...this.formArticle.value,
      lstDeposits: this.rowData,
    };
    console.log(dataCreateUpdate);
  }
  close() {
    this.dialogRef.close();
  }

  addRowLottery() {}
  addColLottery() {}

  protected readonly ClassicEditor = ClassicEditor;

  changeMailContent() {}

  blurTime(type: string) {
    if (type === 'start') {
      if (this.startTime) {
        this.formArticle.patchValue({
          startTime: this.startTime.getHours().toString().padStart(2, '0') + ':' + this.startTime.getMinutes().toString().padStart(2, '0'),
        });
      } else {
        this.formArticle.patchValue({
          startTime: '',
        });
      }
      this.formArticle.get('startTime')?.markAsTouched({ onlySelf: true });
      this.formArticle.get('startTime')?.markAsDirty({ onlySelf: true });
    } else if (type === 'end') {
      if (this.endTime) {
        this.formArticle.patchValue({
          endTime: this.endTime.getHours().toString().padStart(2, '0') + ':' + this.endTime.getMinutes().toString().padStart(2, '0'),
        });
      } else {
        this.formArticle.patchValue({
          endTime: '',
        });
      }
      this.formArticle.get('endTime')?.markAsTouched({ onlySelf: true });
      this.formArticle.get('endTime')?.markAsDirty({ onlySelf: true });
    }
  }
  getLstGroupArticle() {
    this.articleGroupService.getLstArticleGroupCodeAndName().subscribe((res: any) => {
      this.listArticleGroup = res.data;
    });
  }
}
