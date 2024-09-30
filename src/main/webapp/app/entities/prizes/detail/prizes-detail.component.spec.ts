import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrizesDetailComponent } from './prizes-detail.component';

describe('Prizes Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrizesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PrizesDetailComponent,
              resolve: { prizes: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PrizesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load prizes on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PrizesDetailComponent);

      // THEN
      expect(instance.prizes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
