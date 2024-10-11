import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LevelDepositsResultService } from '../service/level-deposits-result.service';
import { ILevelDepositsResult } from '../level-deposits-result.model';
import { LevelDepositsResultFormService } from './level-deposits-result-form.service';

import { LevelDepositsResultUpdateComponent } from './level-deposits-result-update.component';

describe('LevelDepositsResult Management Update Component', () => {
  let comp: LevelDepositsResultUpdateComponent;
  let fixture: ComponentFixture<LevelDepositsResultUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let levelDepositsResultFormService: LevelDepositsResultFormService;
  let levelDepositsResultService: LevelDepositsResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), LevelDepositsResultUpdateComponent],
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
      .overrideTemplate(LevelDepositsResultUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LevelDepositsResultUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    levelDepositsResultFormService = TestBed.inject(LevelDepositsResultFormService);
    levelDepositsResultService = TestBed.inject(LevelDepositsResultService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const levelDepositsResult: ILevelDepositsResult = { id: 456 };

      activatedRoute.data = of({ levelDepositsResult });
      comp.ngOnInit();

      expect(comp.levelDepositsResult).toEqual(levelDepositsResult);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelDepositsResult>>();
      const levelDepositsResult = { id: 123 };
      jest.spyOn(levelDepositsResultFormService, 'getLevelDepositsResult').mockReturnValue(levelDepositsResult);
      jest.spyOn(levelDepositsResultService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelDepositsResult });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: levelDepositsResult }));
      saveSubject.complete();

      // THEN
      expect(levelDepositsResultFormService.getLevelDepositsResult).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(levelDepositsResultService.update).toHaveBeenCalledWith(expect.objectContaining(levelDepositsResult));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelDepositsResult>>();
      const levelDepositsResult = { id: 123 };
      jest.spyOn(levelDepositsResultFormService, 'getLevelDepositsResult').mockReturnValue({ id: null });
      jest.spyOn(levelDepositsResultService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelDepositsResult: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: levelDepositsResult }));
      saveSubject.complete();

      // THEN
      expect(levelDepositsResultFormService.getLevelDepositsResult).toHaveBeenCalled();
      expect(levelDepositsResultService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILevelDepositsResult>>();
      const levelDepositsResult = { id: 123 };
      jest.spyOn(levelDepositsResultService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ levelDepositsResult });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(levelDepositsResultService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
