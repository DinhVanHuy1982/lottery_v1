package com.lottery.app.domain;

import static com.lottery.app.domain.LevelDepositsResultTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelDepositsResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelDepositsResult.class);
        LevelDepositsResult levelDepositsResult1 = getLevelDepositsResultSample1();
        LevelDepositsResult levelDepositsResult2 = new LevelDepositsResult();
        assertThat(levelDepositsResult1).isNotEqualTo(levelDepositsResult2);

        levelDepositsResult2.setId(levelDepositsResult1.getId());
        assertThat(levelDepositsResult1).isEqualTo(levelDepositsResult2);

        levelDepositsResult2 = getLevelDepositsResultSample2();
        assertThat(levelDepositsResult1).isNotEqualTo(levelDepositsResult2);
    }
}
