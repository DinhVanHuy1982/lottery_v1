package com.lottery.app.domain;

import static com.lottery.app.domain.ArticlesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticlesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Articles.class);
        Articles articles1 = getArticlesSample1();
        Articles articles2 = new Articles();
        assertThat(articles1).isNotEqualTo(articles2);

        articles2.setId(articles1.getId());
        assertThat(articles1).isEqualTo(articles2);

        articles2 = getArticlesSample2();
        assertThat(articles1).isNotEqualTo(articles2);
    }
}
