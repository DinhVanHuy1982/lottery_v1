import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LevelDepositsService } from '../service/level-deposits.service';
import { ILevelDeposits } from '../level-deposits.model';
import { LevelDepositsFormService } from './level-deposits-form.service';

import { LevelDepositsUpdateComponent } from './level-deposits-update.component';

describe('LevelDeposits Management Update Component', () => {
  let comp: LevelDepositsUpdateComponent;
  let fixture: ComponentFixture<LevelDepositsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let levelDepositsFormService: LevelDepositsFormService;
  let levelDepositsService: LevelDepositsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LevelDepositsUpdateComponent],
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
      .overrideTemplate(LevelDepositsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LevelDepositsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    levelDepositsFormService = TestBed.inject(LevelDepositsFormService);
    levelDepositsService = TestBed.inject(LevelDepositsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const levelDeposits: ILevelDeposits = { id: 456 };

      activatedRoute.data = of({ levelDeposits });
      comp.ngOnInit();

      expect(comp.levelDeposits).toEqual(levelDeposits);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelDeposits>>();
      const levelDeposits = { id: 123 };
      jest.spyOn(levelDepositsFormService, 'getLevelDeposits').mockReturnValue(levelDeposits);
      jest.spyOn(levelDepositsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelDeposits });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: levelDeposits }));
      saveSubject.complete();

      // THEN
      expect(levelDepositsFormService.getLevelDeposits).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(levelDepositsService.update).toHaveBeenCalledWith(expect.objectContaining(levelDeposits));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelDeposits>>();
      const levelDeposits = { id: 123 };
      jest.spyOn(levelDepositsFormService, 'getLevelDeposits').mockReturnValue({ id: null });
      jest.spyOn(levelDepositsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelDeposits: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: levelDeposits }));
      saveSubject.complete();

      // THEN
      expect(levelDepositsFormService.getLevelDeposits).toHaveBeenCalled();
      expect(levelDepositsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelDeposits>>();
      const levelDeposits = { id: 123 };
      jest.spyOn(levelDepositsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelDeposits });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(levelDepositsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
