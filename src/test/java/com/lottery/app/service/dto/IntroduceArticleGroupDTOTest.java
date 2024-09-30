package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntroduceArticleGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntroduceArticleGroupDTO.class);
        IntroduceArticleGroupDTO introduceArticleGroupDTO1 = new IntroduceArticleGroupDTO();
        introduceArticleGroupDTO1.setId(1L);
        IntroduceArticleGroupDTO introduceArticleGroupDTO2 = new IntroduceArticleGroupDTO();
        assertThat(introduceArticleGroupDTO1).isNotEqualTo(introduceArticleGroupDTO2);
        introduceArticleGroupDTO2.setId(introduceArticleGroupDTO1.getId());
        assertThat(introduceArticleGroupDTO1).isEqualTo(introduceArticleGroupDTO2);
        introduceArticleGroupDTO2.setId(2L);
        assertThat(introduceArticleGroupDTO1).isNotEqualTo(introduceArticleGroupDTO2);
        introduceArticleGroupDTO1.setId(null);
        assertThat(introduceArticleGroupDTO1).isNotEqualTo(introduceArticleGroupDTO2);
    }
}
