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
  totalIntroduce = 0;

  formArticle: FormGroup;
  startTime: any;
  endTime: any;

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
      title: new FormControl('', { validators: [Validators.maxLength(500)] }),
      numberPrize: new FormControl('', { validators: [Validators.required] }),
      articleGroupCode: new FormControl('', { validators: [Validators.maxLength(500)] }),
      numberChoice: new FormControl('', { validators: [Validators.required, Validators.maxLength(500)] }),
      numberOfDigits: new FormControl('', { validators: [Validators.required, Validators.maxLength(500)] }),
      startTime: new FormControl('', { validators: [Validators.required] }),
      endTime: new FormControl('', { validators: [Validators.required, Validators.maxLength(500)] }),
      content: new FormControl(null, { validators: [Validators.maxLength(2000)] }),
      levelDeposits: this.fb.array([]),
    });
    // this.formArticle.get('startTime')?.valueChanges.subscribe((start:Date)=>{
    //   if(start){
    //     this.startTime = start.getHours()+':'+start.getMinutes();
    //   }
    // })
    // this.formArticle.get('endTime')?.valueChanges.subscribe((end:Date)=>{
    //   if(end){
    //     this.endTime = end.getHours()+':'+end.getMinutes();
    //   }
    // })
  }
  ngOnDestroy(): void {}

  ngOnInit(): void {}

  // Truy cập FormArray
  get levelDeposits(): FormArray {
    return this.formArticle.get('levelDeposits') as FormArray;
  }

  // Thêm FormGroup mới vào FormArray
  addItem(): void {
    const newItem = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });

    this.levelDeposits.push(newItem);
  }

  // Xóa một FormGroup khỏi FormArray
  removeItem(index: number): void {
    this.levelDeposits.removeAt(index);
  }

  onSubmit(): void {
    this.formArticle.patchValue({
      startTime: this.startTime.getHours() + ':' + this.startTime.getMinutes() || '',
      endTime: this.endTime.getHours() + ':' + this.endTime.getMinutes() || '',
    });
    console.log(this.formArticle.value);
  }

  getListDocumentInProfileCodeAndTextIdentifier() {}
  initColumnDef() {}

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
    this.dialog.open(AddLevelDepositsComponent, {
      data: { action: 'CR' },
      disableClose: true,
      hasBackdrop: true,
      width: '700px',
      height: '90vh',
      autoFocus: false,
      panelClass: 'rental-info',
    });
  }
  // get subForms(): FormArray {
  //   return this.formArticle.get('levelDeposit') as FormArray;
  // }

  submit() {
    console.log(this.formArticle.value);
  }
  close() {
    this.dialogRef.close();
  }

  addRowLottery() {}
  addColLottery() {}
}
