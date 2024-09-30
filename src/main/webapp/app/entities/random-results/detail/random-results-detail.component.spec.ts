import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RandomResultsDetailComponent } from './random-results-detail.component';

describe('RandomResults Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RandomResultsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RandomResultsDetailComponent,
              resolve: { randomResults: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RandomResultsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load randomResults on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RandomResultsDetailComponent);

      // THEN
      expect(instance.randomResults).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
