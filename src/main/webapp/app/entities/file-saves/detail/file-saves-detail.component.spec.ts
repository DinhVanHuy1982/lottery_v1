import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FileSavesDetailComponent } from './file-saves-detail.component';

describe('FileSaves Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FileSavesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FileSavesDetailComponent,
              resolve: { fileSaves: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FileSavesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load fileSaves on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FileSavesDetailComponent);

      // THEN
      expect(instance.fileSaves).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
