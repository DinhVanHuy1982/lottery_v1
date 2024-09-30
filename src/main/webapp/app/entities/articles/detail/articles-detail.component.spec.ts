import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ArticlesDetailComponent } from './articles-detail.component';

describe('Articles Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticlesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ArticlesDetailComponent,
              resolve: { articles: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ArticlesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load articles on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ArticlesDetailComponent);

      // THEN
      expect(instance.articles).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
