package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelDepositsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelDepositsDTO.class);
        LevelDepositsDTO levelDepositsDTO1 = new LevelDepositsDTO();
        levelDepositsDTO1.setId(1L);
        LevelDepositsDTO levelDepositsDTO2 = new LevelDepositsDTO();
        assertThat(levelDepositsDTO1).isNotEqualTo(levelDepositsDTO2);
        levelDepositsDTO2.setId(levelDepositsDTO1.getId());
        assertThat(levelDepositsDTO1).isEqualTo(levelDepositsDTO2);
        levelDepositsDTO2.setId(2L);
        assertThat(levelDepositsDTO1).isNotEqualTo(levelDepositsDTO2);
        levelDepositsDTO1.setId(null);
        assertThat(levelDepositsDTO1).isNotEqualTo(levelDepositsDTO2);
    }
}
