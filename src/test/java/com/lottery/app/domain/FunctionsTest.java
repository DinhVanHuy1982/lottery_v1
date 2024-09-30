package com.lottery.app.domain;

import static com.lottery.app.domain.FunctionsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FunctionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Functions.class);
        Functions functions1 = getFunctionsSample1();
        Functions functions2 = new Functions();
        assertThat(functions1).isNotEqualTo(functions2);

        functions2.setId(functions1.getId());
        assertThat(functions1).isEqualTo(functions2);

        functions2 = getFunctionsSample2();
        assertThat(functions1).isNotEqualTo(functions2);
    }
}
