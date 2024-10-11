package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelDepositsResultDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelDepositsResultDTO.class);
        LevelDepositsResultDTO levelDepositsResultDTO1 = new LevelDepositsResultDTO();
        levelDepositsResultDTO1.setId(1L);
        LevelDepositsResultDTO levelDepositsResultDTO2 = new LevelDepositsResultDTO();
        assertThat(levelDepositsResultDTO1).isNotEqualTo(levelDepositsResultDTO2);
        levelDepositsResultDTO2.setId(levelDepositsResultDTO1.getId());
        assertThat(levelDepositsResultDTO1).isEqualTo(levelDepositsResultDTO2);
        levelDepositsResultDTO2.setId(2L);
        assertThat(levelDepositsResultDTO1).isNotEqualTo(levelDepositsResultDTO2);
        levelDepositsResultDTO1.setId(null);
        assertThat(levelDepositsResultDTO1).isNotEqualTo(levelDepositsResultDTO2);
    }
}
