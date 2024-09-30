package com.lottery.app.domain;

import static com.lottery.app.domain.IntroduceArticleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntroduceArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntroduceArticle.class);
        IntroduceArticle introduceArticle1 = getIntroduceArticleSample1();
        IntroduceArticle introduceArticle2 = new IntroduceArticle();
        assertThat(introduceArticle1).isNotEqualTo(introduceArticle2);

        introduceArticle2.setId(introduceArticle1.getId());
        assertThat(introduceArticle1).isEqualTo(introduceArticle2);

        introduceArticle2 = getIntroduceArticleSample2();
        assertThat(introduceArticle1).isNotEqualTo(introduceArticle2);
    }
}
