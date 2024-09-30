import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DepositsService } from '../service/deposits.service';
import { IDeposits } from '../deposits.model';
import { DepositsFormService } from './deposits-form.service';

import { DepositsUpdateComponent } from './deposits-update.component';

describe('Deposits Management Update Component', () => {
  let comp: DepositsUpdateComponent;
  let fixture: ComponentFixture<DepositsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let depositsFormService: DepositsFormService;
  let depositsService: DepositsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DepositsUpdateComponent],
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
      .overrideTemplate(DepositsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepositsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    depositsFormService = TestBed.inject(DepositsFormService);
    depositsService = TestBed.inject(DepositsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const deposits: IDeposits = { id: 456 };

      activatedRoute.data = of({ deposits });
      comp.ngOnInit();

      expect(comp.deposits).toEqual(deposits);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeposits>>();
      const deposits = { id: 123 };
      jest.spyOn(depositsFormService, 'getDeposits').mockReturnValue(deposits);
      jest.spyOn(depositsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deposits });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deposits }));
      saveSubject.complete();

      // THEN
      expect(depositsFormService.getDeposits).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(depositsService.update).toHaveBeenCalledWith(expect.objectContaining(deposits));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeposits>>();
      const deposits = { id: 123 };
      jest.spyOn(depositsFormService, 'getDeposits').mockReturnValue({ id: null });
      jest.spyOn(depositsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deposits: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deposits }));
      saveSubject.complete();

      // THEN
      expect(depositsFormService.getDeposits).toHaveBeenCalled();
      expect(depositsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeposits>>();
      const deposits = { id: 123 };
      jest.spyOn(depositsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deposits });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(depositsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
