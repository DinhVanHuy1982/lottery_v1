import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArticleGroupService } from '../service/article-group.service';
import { IArticleGroup } from '../article-group.model';
import { ArticleGroupFormService } from './article-group-form.service';

import { ArticleGroupUpdateComponent } from './article-group-update.component';

describe('ArticleGroup Management Update Component', () => {
  let comp: ArticleGroupUpdateComponent;
  let fixture: ComponentFixture<ArticleGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let articleGroupFormService: ArticleGroupFormService;
  let articleGroupService: ArticleGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ArticleGroupUpdateComponent],
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
      .overrideTemplate(ArticleGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArticleGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    articleGroupFormService = TestBed.inject(ArticleGroupFormService);
    articleGroupService = TestBed.inject(ArticleGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const articleGroup: IArticleGroup = { id: 456 };

      activatedRoute.data = of({ articleGroup });
      comp.ngOnInit();

      expect(comp.articleGroup).toEqual(articleGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleGroup>>();
      const articleGroup = { id: 123 };
      jest.spyOn(articleGroupFormService, 'getArticleGroup').mockReturnValue(articleGroup);
      jest.spyOn(articleGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articleGroup }));
      saveSubject.complete();

      // THEN
      expect(articleGroupFormService.getArticleGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(articleGroupService.update).toHaveBeenCalledWith(expect.objectContaining(articleGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleGroup>>();
      const articleGroup = { id: 123 };
      jest.spyOn(articleGroupFormService, 'getArticleGroup').mockReturnValue({ id: null });
      jest.spyOn(articleGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: articleGroup }));
      saveSubject.complete();

      // THEN
      expect(articleGroupFormService.getArticleGroup).toHaveBeenCalled();
      expect(articleGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArticleGroup>>();
      const articleGroup = { id: 123 };
      jest.spyOn(articleGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ articleGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(articleGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
