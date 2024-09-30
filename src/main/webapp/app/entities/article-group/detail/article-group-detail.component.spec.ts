import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ArticleGroupDetailComponent } from './article-group-detail.component';

describe('ArticleGroup Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleGroupDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ArticleGroupDetailComponent,
              resolve: { articleGroup: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ArticleGroupDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load articleGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ArticleGroupDetailComponent);

      // THEN
      expect(instance.articleGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
