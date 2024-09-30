package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepositsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositsDTO.class);
        DepositsDTO depositsDTO1 = new DepositsDTO();
        depositsDTO1.setId(1L);
        DepositsDTO depositsDTO2 = new DepositsDTO();
        assertThat(depositsDTO1).isNotEqualTo(depositsDTO2);
        depositsDTO2.setId(depositsDTO1.getId());
        assertThat(depositsDTO1).isEqualTo(depositsDTO2);
        depositsDTO2.setId(2L);
        assertThat(depositsDTO1).isNotEqualTo(depositsDTO2);
        depositsDTO1.setId(null);
        assertThat(depositsDTO1).isNotEqualTo(depositsDTO2);
    }
}
