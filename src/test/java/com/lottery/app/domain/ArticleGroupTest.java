package com.lottery.app.domain;

import static com.lottery.app.domain.ArticleGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleGroup.class);
        ArticleGroup articleGroup1 = getArticleGroupSample1();
        ArticleGroup articleGroup2 = new ArticleGroup();
        assertThat(articleGroup1).isNotEqualTo(articleGroup2);

        articleGroup2.setId(articleGroup1.getId());
        assertThat(articleGroup1).isEqualTo(articleGroup2);

        articleGroup2 = getArticleGroupSample2();
        assertThat(articleGroup1).isNotEqualTo(articleGroup2);
    }
}
