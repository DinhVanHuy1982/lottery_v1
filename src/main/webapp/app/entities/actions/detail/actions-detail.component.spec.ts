import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ActionsDetailComponent } from './actions-detail.component';

describe('Actions Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ActionsDetailComponent,
              resolve: { actions: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ActionsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load actions on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ActionsDetailComponent);

      // THEN
      expect(instance.actions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
