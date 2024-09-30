jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AppParamsService } from '../service/app-params.service';

import { AppParamsDeleteDialogComponent } from './app-params-delete-dialog.component';

describe('AppParams Management Delete Component', () => {
  let comp: AppParamsDeleteDialogComponent;
  let fixture: ComponentFixture<AppParamsDeleteDialogComponent>;
  let service: AppParamsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AppParamsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(AppParamsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AppParamsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AppParamsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
