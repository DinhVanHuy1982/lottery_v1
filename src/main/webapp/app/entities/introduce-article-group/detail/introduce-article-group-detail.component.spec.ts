import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IntroduceArticleGroupDetailComponent } from './introduce-article-group-detail.component';

describe('IntroduceArticleGroup Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntroduceArticleGroupDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: IntroduceArticleGroupDetailComponent,
              resolve: { introduceArticleGroup: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IntroduceArticleGroupDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load introduceArticleGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IntroduceArticleGroupDetailComponent);

      // THEN
      expect(instance.introduceArticleGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
