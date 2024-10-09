import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IArticles } from '../articles.model';
import { ArticlesService } from '../service/articles.service';
import { ArticlesFormService, ArticlesFormGroup } from './articles-form.service';
import { NzTimePickerModule } from 'ng-zorro-antd/time-picker';

@Component({
  standalone: true,
  selector: 'jhi-articles-update',
  templateUrl: './articles-update.component.html',
  styleUrls: ['./articles-update.component.scss'],
  imports: [SharedModule, FormsModule, ReactiveFormsModule, NzTimePickerModule],
})
export class ArticlesUpdateComponent implements OnInit {
  isSaving = false;
  articles: IArticles | null = null;
  time = new Date();
  editForm: ArticlesFormGroup = this.articlesFormService.createArticlesFormGroup();

  bodyCreate = {
    name: '',
    title: '',
    content: '',
    numberChoice: '',
    numberOfDigits: '',
    timeStart: '',
    timeEnd: '',
  };

  constructor(
    protected articlesService: ArticlesService,
    protected articlesFormService: ArticlesFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ articles }) => {
      this.articles = articles;
      if (articles) {
        this.updateForm(articles);
      }
    });
  }

  exportTime = { hour: 7, minute: 15, meriden: 'PM', format: 24 };

  onChangeHour(event: any) {
    console.log('event', event);
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const articles = this.articlesFormService.getArticles(this.editForm);
    if (articles.id !== null) {
      this.subscribeToSaveResponse(this.articlesService.update(articles));
    } else {
      this.subscribeToSaveResponse(this.articlesService.create(articles));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticles>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(articles: IArticles): void {
    this.articles = articles;
    this.articlesFormService.resetForm(this.editForm, articles);
  }

  changeTimepicker(selectedTime: any) {
    if (selectedTime) {
      const currentDate = new Date(); // Lấy ngày hiện tại
      const hours = selectedTime.getHours();
      const minutes = selectedTime.getMinutes();

      // Tạo một đối tượng Date mới với ngày hiện tại và thời gian đã chọn
      const time = hours + ':' + minutes;

      console.log('Selected Date and Time:', time);
    }
    // console.log("change time: ",this.time)
  }
}
