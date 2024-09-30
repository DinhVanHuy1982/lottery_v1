package com.lottery.app.domain;

import static com.lottery.app.domain.RoleFunctionsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleFunctionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleFunctions.class);
        RoleFunctions roleFunctions1 = getRoleFunctionsSample1();
        RoleFunctions roleFunctions2 = new RoleFunctions();
        assertThat(roleFunctions1).isNotEqualTo(roleFunctions2);

        roleFunctions2.setId(roleFunctions1.getId());
        assertThat(roleFunctions1).isEqualTo(roleFunctions2);

        roleFunctions2 = getRoleFunctionsSample2();
        assertThat(roleFunctions1).isNotEqualTo(roleFunctions2);
    }
}
