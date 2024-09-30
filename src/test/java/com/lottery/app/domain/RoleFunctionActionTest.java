package com.lottery.app.domain;

import static com.lottery.app.domain.RoleFunctionActionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleFunctionActionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleFunctionAction.class);
        RoleFunctionAction roleFunctionAction1 = getRoleFunctionActionSample1();
        RoleFunctionAction roleFunctionAction2 = new RoleFunctionAction();
        assertThat(roleFunctionAction1).isNotEqualTo(roleFunctionAction2);

        roleFunctionAction2.setId(roleFunctionAction1.getId());
        assertThat(roleFunctionAction1).isEqualTo(roleFunctionAction2);

        roleFunctionAction2 = getRoleFunctionActionSample2();
        assertThat(roleFunctionAction1).isNotEqualTo(roleFunctionAction2);
    }
}
