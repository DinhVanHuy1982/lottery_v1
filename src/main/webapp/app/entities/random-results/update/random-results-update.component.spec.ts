import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RandomResultsService } from '../service/random-results.service';
import { IRandomResults } from '../random-results.model';
import { RandomResultsFormService } from './random-results-form.service';

import { RandomResultsUpdateComponent } from './random-results-update.component';

describe('RandomResults Management Update Component', () => {
  let comp: RandomResultsUpdateComponent;
  let fixture: ComponentFixture<RandomResultsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let randomResultsFormService: RandomResultsFormService;
  let randomResultsService: RandomResultsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), RandomResultsUpdateComponent],
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
      .overrideTemplate(RandomResultsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RandomResultsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    randomResultsFormService = TestBed.inject(RandomResultsFormService);
    randomResultsService = TestBed.inject(RandomResultsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const randomResults: IRandomResults = { id: 456 };

      activatedRoute.data = of({ randomResults });
      comp.ngOnInit();

      expect(comp.randomResults).toEqual(randomResults);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRandomResults>>();
      const randomResults = { id: 123 };
      jest.spyOn(randomResultsFormService, 'getRandomResults').mockReturnValue(randomResults);
      jest.spyOn(randomResultsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ randomResults });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: randomResults }));
      saveSubject.complete();

      // THEN
      expect(randomResultsFormService.getRandomResults).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(randomResultsService.update).toHaveBeenCalledWith(expect.objectContaining(randomResults));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRandomResults>>();
      const randomResults = { id: 123 };
      jest.spyOn(randomResultsFormService, 'getRandomResults').mockReturnValue({ id: null });
      jest.spyOn(randomResultsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ randomResults: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: randomResults }));
      saveSubject.complete();

      // THEN
      expect(randomResultsFormService.getRandomResults).toHaveBeenCalled();
      expect(randomResultsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRandomResults>>();
      const randomResults = { id: 123 };
      jest.spyOn(randomResultsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ randomResults });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(randomResultsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
