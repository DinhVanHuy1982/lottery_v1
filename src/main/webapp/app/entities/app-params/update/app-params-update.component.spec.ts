import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AppParamsService } from '../service/app-params.service';
import { IAppParams } from '../app-params.model';
import { AppParamsFormService } from './app-params-form.service';

import { AppParamsUpdateComponent } from './app-params-update.component';

describe('AppParams Management Update Component', () => {
  let comp: AppParamsUpdateComponent;
  let fixture: ComponentFixture<AppParamsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let appParamsFormService: AppParamsFormService;
  let appParamsService: AppParamsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), AppParamsUpdateComponent],
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
      .overrideTemplate(AppParamsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AppParamsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    appParamsFormService = TestBed.inject(AppParamsFormService);
    appParamsService = TestBed.inject(AppParamsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const appParams: IAppParams = { id: 456 };

      activatedRoute.data = of({ appParams });
      comp.ngOnInit();

      expect(comp.appParams).toEqual(appParams);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppParams>>();
      const appParams = { id: 123 };
      jest.spyOn(appParamsFormService, 'getAppParams').mockReturnValue(appParams);
      jest.spyOn(appParamsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appParams });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appParams }));
      saveSubject.complete();

      // THEN
      expect(appParamsFormService.getAppParams).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(appParamsService.update).toHaveBeenCalledWith(expect.objectContaining(appParams));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppParams>>();
      const appParams = { id: 123 };
      jest.spyOn(appParamsFormService, 'getAppParams').mockReturnValue({ id: null });
      jest.spyOn(appParamsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appParams: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appParams }));
      saveSubject.complete();

      // THEN
      expect(appParamsFormService.getAppParams).toHaveBeenCalled();
      expect(appParamsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppParams>>();
      const appParams = { id: 123 };
      jest.spyOn(appParamsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appParams });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(appParamsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
