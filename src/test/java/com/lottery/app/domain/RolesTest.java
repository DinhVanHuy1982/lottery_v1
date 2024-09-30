package com.lottery.app.domain;

import static com.lottery.app.domain.RolesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RolesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Roles.class);
        Roles roles1 = getRolesSample1();
        Roles roles2 = new Roles();
        assertThat(roles1).isNotEqualTo(roles2);

        roles2.setId(roles1.getId());
        assertThat(roles1).isEqualTo(roles2);

        roles2 = getRolesSample2();
        assertThat(roles1).isNotEqualTo(roles2);
    }
}
