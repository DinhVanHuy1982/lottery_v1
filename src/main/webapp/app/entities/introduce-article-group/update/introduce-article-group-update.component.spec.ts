import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IntroduceArticleGroupService } from '../service/introduce-article-group.service';
import { IIntroduceArticleGroup } from '../introduce-article-group.model';
import { IntroduceArticleGroupFormService } from './introduce-article-group-form.service';

import { IntroduceArticleGroupUpdateComponent } from './introduce-article-group-update.component';

describe('IntroduceArticleGroup Management Update Component', () => {
  let comp: IntroduceArticleGroupUpdateComponent;
  let fixture: ComponentFixture<IntroduceArticleGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let introduceArticleGroupFormService: IntroduceArticleGroupFormService;
  let introduceArticleGroupService: IntroduceArticleGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), IntroduceArticleGroupUpdateComponent],
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
      .overrideTemplate(IntroduceArticleGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IntroduceArticleGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    introduceArticleGroupFormService = TestBed.inject(IntroduceArticleGroupFormService);
    introduceArticleGroupService = TestBed.inject(IntroduceArticleGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const introduceArticleGroup: IIntroduceArticleGroup = { id: 456 };

      activatedRoute.data = of({ introduceArticleGroup });
      comp.ngOnInit();

      expect(comp.introduceArticleGroup).toEqual(introduceArticleGroup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntroduceArticleGroup>>();
      const introduceArticleGroup = { id: 123 };
      jest.spyOn(introduceArticleGroupFormService, 'getIntroduceArticleGroup').mockReturnValue(introduceArticleGroup);
      jest.spyOn(introduceArticleGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ introduceArticleGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: introduceArticleGroup }));
      saveSubject.complete();

      // THEN
      expect(introduceArticleGroupFormService.getIntroduceArticleGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(introduceArticleGroupService.update).toHaveBeenCalledWith(expect.objectContaining(introduceArticleGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntroduceArticleGroup>>();
      const introduceArticleGroup = { id: 123 };
      jest.spyOn(introduceArticleGroupFormService, 'getIntroduceArticleGroup').mockReturnValue({ id: null });
      jest.spyOn(introduceArticleGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ introduceArticleGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: introduceArticleGroup }));
      saveSubject.complete();

      // THEN
      expect(introduceArticleGroupFormService.getIntroduceArticleGroup).toHaveBeenCalled();
      expect(introduceArticleGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntroduceArticleGroup>>();
      const introduceArticleGroup = { id: 123 };
      jest.spyOn(introduceArticleGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ introduceArticleGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(introduceArticleGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
