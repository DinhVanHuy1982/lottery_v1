package com.lottery.app.domain;

import static com.lottery.app.domain.LevelDepositsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelDepositsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelDeposits.class);
        LevelDeposits levelDeposits1 = getLevelDepositsSample1();
        LevelDeposits levelDeposits2 = new LevelDeposits();
        assertThat(levelDeposits1).isNotEqualTo(levelDeposits2);

        levelDeposits2.setId(levelDeposits1.getId());
        assertThat(levelDeposits1).isEqualTo(levelDeposits2);

        levelDeposits2 = getLevelDepositsSample2();
        assertThat(levelDeposits1).isNotEqualTo(levelDeposits2);
    }
}
