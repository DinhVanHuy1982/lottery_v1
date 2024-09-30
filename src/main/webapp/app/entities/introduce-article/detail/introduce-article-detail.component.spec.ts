import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IntroduceArticleDetailComponent } from './introduce-article-detail.component';

describe('IntroduceArticle Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntroduceArticleDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: IntroduceArticleDetailComponent,
              resolve: { introduceArticle: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IntroduceArticleDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load introduceArticle on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IntroduceArticleDetailComponent);

      // THEN
      expect(instance.introduceArticle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
