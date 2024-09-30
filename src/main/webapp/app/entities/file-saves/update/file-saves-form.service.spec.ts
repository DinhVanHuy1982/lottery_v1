import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../file-saves.test-samples';

import { FileSavesFormService } from './file-saves-form.service';

describe('FileSaves Form Service', () => {
  let service: FileSavesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FileSavesFormService);
  });

  describe('Service methods', () => {
    describe('createFileSavesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFileSavesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileId: expect.any(Object),
            fileName: expect.any(Object),
            filePath: expect.any(Object),
          }),
        );
      });

      it('passing IFileSaves should create a new form with FormGroup', () => {
        const formGroup = service.createFileSavesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileId: expect.any(Object),
            fileName: expect.any(Object),
            filePath: expect.any(Object),
          }),
        );
      });
    });

    describe('getFileSaves', () => {
      it('should return NewFileSaves for default FileSaves initial value', () => {
        const formGroup = service.createFileSavesFormGroup(sampleWithNewData);

        const fileSaves = service.getFileSaves(formGroup) as any;

        expect(fileSaves).toMatchObject(sampleWithNewData);
      });

      it('should return NewFileSaves for empty FileSaves initial value', () => {
        const formGroup = service.createFileSavesFormGroup();

        const fileSaves = service.getFileSaves(formGroup) as any;

        expect(fileSaves).toMatchObject({});
      });

      it('should return IFileSaves', () => {
        const formGroup = service.createFileSavesFormGroup(sampleWithRequiredData);

        const fileSaves = service.getFileSaves(formGroup) as any;

        expect(fileSaves).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFileSaves should not enable id FormControl', () => {
        const formGroup = service.createFileSavesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFileSaves should disable id FormControl', () => {
        const formGroup = service.createFileSavesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
