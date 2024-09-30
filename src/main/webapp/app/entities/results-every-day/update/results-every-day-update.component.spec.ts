import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResultsEveryDayService } from '../service/results-every-day.service';
import { IResultsEveryDay } from '../results-every-day.model';
import { ResultsEveryDayFormService } from './results-every-day-form.service';

import { ResultsEveryDayUpdateComponent } from './results-every-day-update.component';

describe('ResultsEveryDay Management Update Component', () => {
  let comp: ResultsEveryDayUpdateComponent;
  let fixture: ComponentFixture<ResultsEveryDayUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resultsEveryDayFormService: ResultsEveryDayFormService;
  let resultsEveryDayService: ResultsEveryDayService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ResultsEveryDayUpdateComponent],
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
      .overrideTemplate(ResultsEveryDayUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResultsEveryDayUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resultsEveryDayFormService = TestBed.inject(ResultsEveryDayFormService);
    resultsEveryDayService = TestBed.inject(ResultsEveryDayService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const resultsEveryDay: IResultsEveryDay = { id: 456 };

      activatedRoute.data = of({ resultsEveryDay });
      comp.ngOnInit();

      expect(comp.resultsEveryDay).toEqual(resultsEveryDay);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultsEveryDay>>();
      const resultsEveryDay = { id: 123 };
      jest.spyOn(resultsEveryDayFormService, 'getResultsEveryDay').mockReturnValue(resultsEveryDay);
      jest.spyOn(resultsEveryDayService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultsEveryDay });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultsEveryDay }));
      saveSubject.complete();

      // THEN
      expect(resultsEveryDayFormService.getResultsEveryDay).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(resultsEveryDayService.update).toHaveBeenCalledWith(expect.objectContaining(resultsEveryDay));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultsEveryDay>>();
      const resultsEveryDay = { id: 123 };
      jest.spyOn(resultsEveryDayFormService, 'getResultsEveryDay').mockReturnValue({ id: null });
      jest.spyOn(resultsEveryDayService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultsEveryDay: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultsEveryDay }));
      saveSubject.complete();

      // THEN
      expect(resultsEveryDayFormService.getResultsEveryDay).toHaveBeenCalled();
      expect(resultsEveryDayService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultsEveryDay>>();
      const resultsEveryDay = { id: 123 };
      jest.spyOn(resultsEveryDayService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultsEveryDay });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resultsEveryDayService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
