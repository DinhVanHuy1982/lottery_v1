import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RoleFunctionsDetailComponent } from './role-functions-detail.component';

describe('RoleFunctions Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleFunctionsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RoleFunctionsDetailComponent,
              resolve: { roleFunctions: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RoleFunctionsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load roleFunctions on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RoleFunctionsDetailComponent);

      // THEN
      expect(instance.roleFunctions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
