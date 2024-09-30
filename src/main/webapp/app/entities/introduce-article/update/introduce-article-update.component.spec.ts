import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IntroduceArticleService } from '../service/introduce-article.service';
import { IIntroduceArticle } from '../introduce-article.model';
import { IntroduceArticleFormService } from './introduce-article-form.service';

import { IntroduceArticleUpdateComponent } from './introduce-article-update.component';

describe('IntroduceArticle Management Update Component', () => {
  let comp: IntroduceArticleUpdateComponent;
  let fixture: ComponentFixture<IntroduceArticleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let introduceArticleFormService: IntroduceArticleFormService;
  let introduceArticleService: IntroduceArticleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), IntroduceArticleUpdateComponent],
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
      .overrideTemplate(IntroduceArticleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IntroduceArticleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    introduceArticleFormService = TestBed.inject(IntroduceArticleFormService);
    introduceArticleService = TestBed.inject(IntroduceArticleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const introduceArticle: IIntroduceArticle = { id: 456 };

      activatedRoute.data = of({ introduceArticle });
      comp.ngOnInit();

      expect(comp.introduceArticle).toEqual(introduceArticle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntroduceArticle>>();
      const introduceArticle = { id: 123 };
      jest.spyOn(introduceArticleFormService, 'getIntroduceArticle').mockReturnValue(introduceArticle);
      jest.spyOn(introduceArticleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ introduceArticle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: introduceArticle }));
      saveSubject.complete();

      // THEN
      expect(introduceArticleFormService.getIntroduceArticle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(introduceArticleService.update).toHaveBeenCalledWith(expect.objectContaining(introduceArticle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntroduceArticle>>();
      const introduceArticle = { id: 123 };
      jest.spyOn(introduceArticleFormService, 'getIntroduceArticle').mockReturnValue({ id: null });
      jest.spyOn(introduceArticleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ introduceArticle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: introduceArticle }));
      saveSubject.complete();

      // THEN
      expect(introduceArticleFormService.getIntroduceArticle).toHaveBeenCalled();
      expect(introduceArticleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntroduceArticle>>();
      const introduceArticle = { id: 123 };
      jest.spyOn(introduceArticleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ introduceArticle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(introduceArticleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
