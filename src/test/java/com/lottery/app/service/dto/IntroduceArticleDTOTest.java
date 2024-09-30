package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntroduceArticleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntroduceArticleDTO.class);
        IntroduceArticleDTO introduceArticleDTO1 = new IntroduceArticleDTO();
        introduceArticleDTO1.setId(1L);
        IntroduceArticleDTO introduceArticleDTO2 = new IntroduceArticleDTO();
        assertThat(introduceArticleDTO1).isNotEqualTo(introduceArticleDTO2);
        introduceArticleDTO2.setId(introduceArticleDTO1.getId());
        assertThat(introduceArticleDTO1).isEqualTo(introduceArticleDTO2);
        introduceArticleDTO2.setId(2L);
        assertThat(introduceArticleDTO1).isNotEqualTo(introduceArticleDTO2);
        introduceArticleDTO1.setId(null);
        assertThat(introduceArticleDTO1).isNotEqualTo(introduceArticleDTO2);
    }
}
