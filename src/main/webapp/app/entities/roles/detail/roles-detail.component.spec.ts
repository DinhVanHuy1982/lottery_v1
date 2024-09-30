import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RolesDetailComponent } from './roles-detail.component';

describe('Roles Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RolesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RolesDetailComponent,
              resolve: { roles: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RolesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load roles on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RolesDetailComponent);

      // THEN
      expect(instance.roles).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
