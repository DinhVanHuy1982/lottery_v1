import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IArticles } from '../articles.model';
import { EntityArrayResponseType, ArticlesService } from '../service/articles.service';
import { ArticlesDeleteDialogComponent } from '../delete/articles-delete-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { CreateUpdateArticlesComponent } from './create-update-articles/create-update-articles.component';

@Component({
  standalone: true,
  selector: 'jhi-articles',
  templateUrl: './articles.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ItemCountComponent,
  ],
})
export class ArticlesComponent implements OnInit {
  articles?: IArticles[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  sort = 'name';
  sortType = 'ASC';
  page = 1;
  bodySearch = {
    textSearch: '',
  };

  constructor(
    protected articlesService: ArticlesService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    private dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    this.initColumnDef();
    this.searchArticle(1);
  }

  searchArticle(page: number) {
    this.page = page;
    const dataSearch = {
      page: page,
      pageSize: this.itemsPerPage,
      sort: this.sort,
      sortType: this.sortType,
      data: {},
    };
    this.articlesService.searchArticle(dataSearch).subscribe(res => {
      console.log('data search: ', res);
    });
  }

  openCreateArticle() {
    this.dialog.open(CreateUpdateArticlesComponent, {
      data: { action: 'CR' },
      disableClose: true,
      hasBackdrop: true,
      width: '1030px',
      height: '90vh',
      autoFocus: false,
      panelClass: 'rental-info',
    });
  }
  initColumnDef() {}
}
