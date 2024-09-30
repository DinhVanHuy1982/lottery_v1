package com.lottery.app.domain;

import static com.lottery.app.domain.AppParamsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppParamsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppParams.class);
        AppParams appParams1 = getAppParamsSample1();
        AppParams appParams2 = new AppParams();
        assertThat(appParams1).isNotEqualTo(appParams2);

        appParams2.setId(appParams1.getId());
        assertThat(appParams1).isEqualTo(appParams2);

        appParams2 = getAppParamsSample2();
        assertThat(appParams1).isNotEqualTo(appParams2);
    }
}
