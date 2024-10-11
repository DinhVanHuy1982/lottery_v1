import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LevelDepositsDetailComponent } from './level-deposits-detail.component';

describe('LevelDeposits Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LevelDepositsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LevelDepositsDetailComponent,
              resolve: { levelDeposits: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LevelDepositsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load levelDeposits on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LevelDepositsDetailComponent);

      // THEN
      expect(instance.levelDeposits).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
