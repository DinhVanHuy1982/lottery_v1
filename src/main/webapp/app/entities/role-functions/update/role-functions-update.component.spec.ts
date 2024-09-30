import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RoleFunctionsService } from '../service/role-functions.service';
import { IRoleFunctions } from '../role-functions.model';
import { RoleFunctionsFormService } from './role-functions-form.service';

import { RoleFunctionsUpdateComponent } from './role-functions-update.component';

describe('RoleFunctions Management Update Component', () => {
  let comp: RoleFunctionsUpdateComponent;
  let fixture: ComponentFixture<RoleFunctionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let roleFunctionsFormService: RoleFunctionsFormService;
  let roleFunctionsService: RoleFunctionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), RoleFunctionsUpdateComponent],
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
      .overrideTemplate(RoleFunctionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleFunctionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    roleFunctionsFormService = TestBed.inject(RoleFunctionsFormService);
    roleFunctionsService = TestBed.inject(RoleFunctionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const roleFunctions: IRoleFunctions = { id: 456 };

      activatedRoute.data = of({ roleFunctions });
      comp.ngOnInit();

      expect(comp.roleFunctions).toEqual(roleFunctions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleFunctions>>();
      const roleFunctions = { id: 123 };
      jest.spyOn(roleFunctionsFormService, 'getRoleFunctions').mockReturnValue(roleFunctions);
      jest.spyOn(roleFunctionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleFunctions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleFunctions }));
      saveSubject.complete();

      // THEN
      expect(roleFunctionsFormService.getRoleFunctions).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(roleFunctionsService.update).toHaveBeenCalledWith(expect.objectContaining(roleFunctions));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleFunctions>>();
      const roleFunctions = { id: 123 };
      jest.spyOn(roleFunctionsFormService, 'getRoleFunctions').mockReturnValue({ id: null });
      jest.spyOn(roleFunctionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleFunctions: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleFunctions }));
      saveSubject.complete();

      // THEN
      expect(roleFunctionsFormService.getRoleFunctions).toHaveBeenCalled();
      expect(roleFunctionsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoleFunctions>>();
      const roleFunctions = { id: 123 };
      jest.spyOn(roleFunctionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleFunctions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(roleFunctionsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
