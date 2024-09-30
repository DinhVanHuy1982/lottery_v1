import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ActionsService } from '../service/actions.service';
import { IActions } from '../actions.model';
import { ActionsFormService } from './actions-form.service';

import { ActionsUpdateComponent } from './actions-update.component';

describe('Actions Management Update Component', () => {
  let comp: ActionsUpdateComponent;
  let fixture: ComponentFixture<ActionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let actionsFormService: ActionsFormService;
  let actionsService: ActionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ActionsUpdateComponent],
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
      .overrideTemplate(ActionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ActionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    actionsFormService = TestBed.inject(ActionsFormService);
    actionsService = TestBed.inject(ActionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const actions: IActions = { id: 456 };

      activatedRoute.data = of({ actions });
      comp.ngOnInit();

      expect(comp.actions).toEqual(actions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActions>>();
      const actions = { id: 123 };
      jest.spyOn(actionsFormService, 'getActions').mockReturnValue(actions);
      jest.spyOn(actionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ actions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: actions }));
      saveSubject.complete();

      // THEN
      expect(actionsFormService.getActions).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(actionsService.update).toHaveBeenCalledWith(expect.objectContaining(actions));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActions>>();
      const actions = { id: 123 };
      jest.spyOn(actionsFormService, 'getActions').mockReturnValue({ id: null });
      jest.spyOn(actionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ actions: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: actions }));
      saveSubject.complete();

      // THEN
      expect(actionsFormService.getActions).toHaveBeenCalled();
      expect(actionsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActions>>();
      const actions = { id: 123 };
      jest.spyOn(actionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ actions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(actionsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
