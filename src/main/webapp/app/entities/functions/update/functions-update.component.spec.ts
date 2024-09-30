import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FunctionsService } from '../service/functions.service';
import { IFunctions } from '../functions.model';
import { FunctionsFormService } from './functions-form.service';

import { FunctionsUpdateComponent } from './functions-update.component';

describe('Functions Management Update Component', () => {
  let comp: FunctionsUpdateComponent;
  let fixture: ComponentFixture<FunctionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let functionsFormService: FunctionsFormService;
  let functionsService: FunctionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), FunctionsUpdateComponent],
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
      .overrideTemplate(FunctionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FunctionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    functionsFormService = TestBed.inject(FunctionsFormService);
    functionsService = TestBed.inject(FunctionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const functions: IFunctions = { id: 456 };

      activatedRoute.data = of({ functions });
      comp.ngOnInit();

      expect(comp.functions).toEqual(functions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunctions>>();
      const functions = { id: 123 };
      jest.spyOn(functionsFormService, 'getFunctions').mockReturnValue(functions);
      jest.spyOn(functionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ functions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: functions }));
      saveSubject.complete();

      // THEN
      expect(functionsFormService.getFunctions).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(functionsService.update).toHaveBeenCalledWith(expect.objectContaining(functions));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunctions>>();
      const functions = { id: 123 };
      jest.spyOn(functionsFormService, 'getFunctions').mockReturnValue({ id: null });
      jest.spyOn(functionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ functions: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: functions }));
      saveSubject.complete();

      // THEN
      expect(functionsFormService.getFunctions).toHaveBeenCalled();
      expect(functionsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunctions>>();
      const functions = { id: 123 };
      jest.spyOn(functionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ functions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(functionsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
