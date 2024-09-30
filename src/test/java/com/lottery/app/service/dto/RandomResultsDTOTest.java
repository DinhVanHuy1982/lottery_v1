package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RandomResultsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RandomResultsDTO.class);
        RandomResultsDTO randomResultsDTO1 = new RandomResultsDTO();
        randomResultsDTO1.setId(1L);
        RandomResultsDTO randomResultsDTO2 = new RandomResultsDTO();
        assertThat(randomResultsDTO1).isNotEqualTo(randomResultsDTO2);
        randomResultsDTO2.setId(randomResultsDTO1.getId());
        assertThat(randomResultsDTO1).isEqualTo(randomResultsDTO2);
        randomResultsDTO2.setId(2L);
        assertThat(randomResultsDTO1).isNotEqualTo(randomResultsDTO2);
        randomResultsDTO1.setId(null);
        assertThat(randomResultsDTO1).isNotEqualTo(randomResultsDTO2);
    }
}
