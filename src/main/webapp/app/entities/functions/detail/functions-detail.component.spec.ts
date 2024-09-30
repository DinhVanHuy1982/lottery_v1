import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FunctionsDetailComponent } from './functions-detail.component';

describe('Functions Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FunctionsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FunctionsDetailComponent,
              resolve: { functions: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FunctionsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load functions on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FunctionsDetailComponent);

      // THEN
      expect(instance.functions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
