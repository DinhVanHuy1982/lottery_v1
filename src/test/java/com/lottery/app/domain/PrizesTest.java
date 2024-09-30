package com.lottery.app.domain;

import static com.lottery.app.domain.PrizesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrizesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prizes.class);
        Prizes prizes1 = getPrizesSample1();
        Prizes prizes2 = new Prizes();
        assertThat(prizes1).isNotEqualTo(prizes2);

        prizes2.setId(prizes1.getId());
        assertThat(prizes1).isEqualTo(prizes2);

        prizes2 = getPrizesSample2();
        assertThat(prizes1).isNotEqualTo(prizes2);
    }
}
