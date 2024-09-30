package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrizesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrizesDTO.class);
        PrizesDTO prizesDTO1 = new PrizesDTO();
        prizesDTO1.setId(1L);
        PrizesDTO prizesDTO2 = new PrizesDTO();
        assertThat(prizesDTO1).isNotEqualTo(prizesDTO2);
        prizesDTO2.setId(prizesDTO1.getId());
        assertThat(prizesDTO1).isEqualTo(prizesDTO2);
        prizesDTO2.setId(2L);
        assertThat(prizesDTO1).isNotEqualTo(prizesDTO2);
        prizesDTO1.setId(null);
        assertThat(prizesDTO1).isNotEqualTo(prizesDTO2);
    }
}
