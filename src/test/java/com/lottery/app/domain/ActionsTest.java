package com.lottery.app.domain;

import static com.lottery.app.domain.ActionsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actions.class);
        Actions actions1 = getActionsSample1();
        Actions actions2 = new Actions();
        assertThat(actions1).isNotEqualTo(actions2);

        actions2.setId(actions1.getId());
        assertThat(actions1).isEqualTo(actions2);

        actions2 = getActionsSample2();
        assertThat(actions1).isNotEqualTo(actions2);
    }
}
