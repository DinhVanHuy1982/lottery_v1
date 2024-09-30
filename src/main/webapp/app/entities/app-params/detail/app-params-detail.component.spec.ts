import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AppParamsDetailComponent } from './app-params-detail.component';

describe('AppParams Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppParamsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AppParamsDetailComponent,
              resolve: { appParams: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AppParamsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load appParams on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AppParamsDetailComponent);

      // THEN
      expect(instance.appParams).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
