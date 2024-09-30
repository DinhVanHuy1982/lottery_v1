package com.lottery.app.domain;

import static com.lottery.app.domain.DepositsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepositsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deposits.class);
        Deposits deposits1 = getDepositsSample1();
        Deposits deposits2 = new Deposits();
        assertThat(deposits1).isNotEqualTo(deposits2);

        deposits2.setId(deposits1.getId());
        assertThat(deposits1).isEqualTo(deposits2);

        deposits2 = getDepositsSample2();
        assertThat(deposits1).isNotEqualTo(deposits2);
    }
}
