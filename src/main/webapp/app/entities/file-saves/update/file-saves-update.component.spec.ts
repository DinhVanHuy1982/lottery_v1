import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FileSavesService } from '../service/file-saves.service';
import { IFileSaves } from '../file-saves.model';
import { FileSavesFormService } from './file-saves-form.service';

import { FileSavesUpdateComponent } from './file-saves-update.component';

describe('FileSaves Management Update Component', () => {
  let comp: FileSavesUpdateComponent;
  let fixture: ComponentFixture<FileSavesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fileSavesFormService: FileSavesFormService;
  let fileSavesService: FileSavesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), FileSavesUpdateComponent],
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
      .overrideTemplate(FileSavesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FileSavesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fileSavesFormService = TestBed.inject(FileSavesFormService);
    fileSavesService = TestBed.inject(FileSavesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fileSaves: IFileSaves = { id: 456 };

      activatedRoute.data = of({ fileSaves });
      comp.ngOnInit();

      expect(comp.fileSaves).toEqual(fileSaves);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFileSaves>>();
      const fileSaves = { id: 123 };
      jest.spyOn(fileSavesFormService, 'getFileSaves').mockReturnValue(fileSaves);
      jest.spyOn(fileSavesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fileSaves });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fileSaves }));
      saveSubject.complete();

      // THEN
      expect(fileSavesFormService.getFileSaves).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fileSavesService.update).toHaveBeenCalledWith(expect.objectContaining(fileSaves));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFileSaves>>();
      const fileSaves = { id: 123 };
      jest.spyOn(fileSavesFormService, 'getFileSaves').mockReturnValue({ id: null });
      jest.spyOn(fileSavesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fileSaves: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fileSaves }));
      saveSubject.complete();

      // THEN
      expect(fileSavesFormService.getFileSaves).toHaveBeenCalled();
      expect(fileSavesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFileSaves>>();
      const fileSaves = { id: 123 };
      jest.spyOn(fileSavesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fileSaves });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fileSavesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
