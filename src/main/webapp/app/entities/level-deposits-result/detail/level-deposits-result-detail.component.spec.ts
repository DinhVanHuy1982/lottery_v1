import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LevelDepositsResultDetailComponent } from './level-deposits-result-detail.component';

describe('LevelDepositsResult Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LevelDepositsResultDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LevelDepositsResultDetailComponent,
              resolve: { levelDepositsResult: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LevelDepositsResultDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load levelDepositsResult on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LevelDepositsResultDetailComponent);

      // THEN
      expect(instance.levelDepositsResult).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
