import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RoleFunctionActionDetailComponent } from './role-function-action-detail.component';

describe('RoleFunctionAction Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleFunctionActionDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RoleFunctionActionDetailComponent,
              resolve: { roleFunctionAction: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RoleFunctionActionDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load roleFunctionAction on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RoleFunctionActionDetailComponent);

      // THEN
      expect(instance.roleFunctionAction).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
