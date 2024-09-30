package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleGroupDTO.class);
        ArticleGroupDTO articleGroupDTO1 = new ArticleGroupDTO();
        articleGroupDTO1.setId(1L);
        ArticleGroupDTO articleGroupDTO2 = new ArticleGroupDTO();
        assertThat(articleGroupDTO1).isNotEqualTo(articleGroupDTO2);
        articleGroupDTO2.setId(articleGroupDTO1.getId());
        assertThat(articleGroupDTO1).isEqualTo(articleGroupDTO2);
        articleGroupDTO2.setId(2L);
        assertThat(articleGroupDTO1).isNotEqualTo(articleGroupDTO2);
        articleGroupDTO1.setId(null);
        assertThat(articleGroupDTO1).isNotEqualTo(articleGroupDTO2);
    }
}
