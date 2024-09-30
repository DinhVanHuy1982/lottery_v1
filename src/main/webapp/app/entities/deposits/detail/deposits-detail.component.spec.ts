import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DepositsDetailComponent } from './deposits-detail.component';

describe('Deposits Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepositsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DepositsDetailComponent,
              resolve: { deposits: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DepositsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load deposits on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DepositsDetailComponent);

      // THEN
      expect(instance.deposits).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
