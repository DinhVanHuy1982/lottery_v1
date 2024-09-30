import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArticlesService } from '../service/articles.service';
import { IArticles } from '../articles.model';
import { ArticlesFormService } from './articles-form.service';

import { ArticlesUpdateComponent } from './articles-update.component';

describe('Articles Management Update Component', () => {
  let comp: ArticlesUpdateComponent;
  let fixture: ComponentFixture<ArticlesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let articlesFormService: ArticlesFormService;
  let articlesService: ArticlesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ArticlesUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ArticlesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArticlesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    articlesFormService = TestBed.inject(ArticlesFormService);
    articlesService = TestBed.inject(ArticlesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const articles: IArticles = { id: 456 };

      activatedRoute.data = of({ articles });
      comp.ngOnInit();

      expect(comp.articles).toEqual(articles);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticles>>();
      const articles = { id: 123 };
      jest.spyOn(articlesFormService, 'getArticles').mockReturnValue(articles);
      jest.spyOn(articlesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articles }));
      saveSubject.complete();

      // THEN
      expect(articlesFormService.getArticles).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(articlesService.update).toHaveBeenCalledWith(expect.objectContaining(articles));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticles>>();
      const articles = { id: 123 };
      jest.spyOn(articlesFormService, 'getArticles').mockReturnValue({ id: null });
      jest.spyOn(articlesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articles: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articles }));
      saveSubject.complete();

      // THEN
      expect(articlesFormService.getArticles).toHaveBeenCalled();
      expect(articlesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticles>>();
      const articles = { id: 123 };
      jest.spyOn(articlesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(articlesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
