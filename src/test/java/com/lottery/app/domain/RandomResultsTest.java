package com.lottery.app.domain;

import static com.lottery.app.domain.RandomResultsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RandomResultsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RandomResults.class);
        RandomResults randomResults1 = getRandomResultsSample1();
        RandomResults randomResults2 = new RandomResults();
        assertThat(randomResults1).isNotEqualTo(randomResults2);

        randomResults2.setId(randomResults1.getId());
        assertThat(randomResults1).isEqualTo(randomResults2);

        randomResults2 = getRandomResultsSample2();
        assertThat(randomResults1).isNotEqualTo(randomResults2);
    }
}
