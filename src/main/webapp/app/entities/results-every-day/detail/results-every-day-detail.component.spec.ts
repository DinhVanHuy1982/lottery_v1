import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ResultsEveryDayDetailComponent } from './results-every-day-detail.component';

describe('ResultsEveryDay Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultsEveryDayDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ResultsEveryDayDetailComponent,
              resolve: { resultsEveryDay: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ResultsEveryDayDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load resultsEveryDay on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ResultsEveryDayDetailComponent);

      // THEN
      expect(instance.resultsEveryDay).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
