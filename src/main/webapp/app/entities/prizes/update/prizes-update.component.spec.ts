import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrizesService } from '../service/prizes.service';
import { IPrizes } from '../prizes.model';
import { PrizesFormService } from './prizes-form.service';

import { PrizesUpdateComponent } from './prizes-update.component';

describe('Prizes Management Update Component', () => {
  let comp: PrizesUpdateComponent;
  let fixture: ComponentFixture<PrizesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prizesFormService: PrizesFormService;
  let prizesService: PrizesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PrizesUpdateComponent],
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
      .overrideTemplate(PrizesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrizesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prizesFormService = TestBed.inject(PrizesFormService);
    prizesService = TestBed.inject(PrizesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const prizes: IPrizes = { id: 456 };

      activatedRoute.data = of({ prizes });
      comp.ngOnInit();

      expect(comp.prizes).toEqual(prizes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrizes>>();
      const prizes = { id: 123 };
      jest.spyOn(prizesFormService, 'getPrizes').mockReturnValue(prizes);
      jest.spyOn(prizesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prizes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prizes }));
      saveSubject.complete();

      // THEN
      expect(prizesFormService.getPrizes).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prizesService.update).toHaveBeenCalledWith(expect.objectContaining(prizes));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrizes>>();
      const prizes = { id: 123 };
      jest.spyOn(prizesFormService, 'getPrizes').mockReturnValue({ id: null });
      jest.spyOn(prizesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prizes: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prizes }));
      saveSubject.complete();

      // THEN
      expect(prizesFormService.getPrizes).toHaveBeenCalled();
      expect(prizesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrizes>>();
      const prizes = { id: 123 };
      jest.spyOn(prizesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prizes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prizesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
