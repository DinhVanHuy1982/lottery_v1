package com.lottery.app.domain;

import static com.lottery.app.domain.IntroduceArticleGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntroduceArticleGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntroduceArticleGroup.class);
        IntroduceArticleGroup introduceArticleGroup1 = getIntroduceArticleGroupSample1();
        IntroduceArticleGroup introduceArticleGroup2 = new IntroduceArticleGroup();
        assertThat(introduceArticleGroup1).isNotEqualTo(introduceArticleGroup2);

        introduceArticleGroup2.setId(introduceArticleGroup1.getId());
        assertThat(introduceArticleGroup1).isEqualTo(introduceArticleGroup2);

        introduceArticleGroup2 = getIntroduceArticleGroupSample2();
        assertThat(introduceArticleGroup1).isNotEqualTo(introduceArticleGroup2);
    }
}
