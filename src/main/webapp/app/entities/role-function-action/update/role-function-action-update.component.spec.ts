import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RoleFunctionActionService } from '../service/role-function-action.service';
import { IRoleFunctionAction } from '../role-function-action.model';
import { RoleFunctionActionFormService } from './role-function-action-form.service';

import { RoleFunctionActionUpdateComponent } from './role-function-action-update.component';

describe('RoleFunctionAction Management Update Component', () => {
  let comp: RoleFunctionActionUpdateComponent;
  let fixture: ComponentFixture<RoleFunctionActionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let roleFunctionActionFormService: RoleFunctionActionFormService;
  let roleFunctionActionService: RoleFunctionActionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), RoleFunctionActionUpdateComponent],
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
      .overrideTemplate(RoleFunctionActionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleFunctionActionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    roleFunctionActionFormService = TestBed.inject(RoleFunctionActionFormService);
    roleFunctionActionService = TestBed.inject(RoleFunctionActionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const roleFunctionAction: IRoleFunctionAction = { id: 456 };

      activatedRoute.data = of({ roleFunctionAction });
      comp.ngOnInit();

      expect(comp.roleFunctionAction).toEqual(roleFunctionAction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleFunctionAction>>();
      const roleFunctionAction = { id: 123 };
      jest.spyOn(roleFunctionActionFormService, 'getRoleFunctionAction').mockReturnValue(roleFunctionAction);
      jest.spyOn(roleFunctionActionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleFunctionAction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleFunctionAction }));
      saveSubject.complete();

      // THEN
      expect(roleFunctionActionFormService.getRoleFunctionAction).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(roleFunctionActionService.update).toHaveBeenCalledWith(expect.objectContaining(roleFunctionAction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleFunctionAction>>();
      const roleFunctionAction = { id: 123 };
      jest.spyOn(roleFunctionActionFormService, 'getRoleFunctionAction').mockReturnValue({ id: null });
      jest.spyOn(roleFunctionActionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleFunctionAction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleFunctionAction }));
      saveSubject.complete();

      // THEN
      expect(roleFunctionActionFormService.getRoleFunctionAction).toHaveBeenCalled();
      expect(roleFunctionActionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleFunctionAction>>();
      const roleFunctionAction = { id: 123 };
      jest.spyOn(roleFunctionActionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleFunctionAction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(roleFunctionActionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
