package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppParamsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppParamsDTO.class);
        AppParamsDTO appParamsDTO1 = new AppParamsDTO();
        appParamsDTO1.setId(1L);
        AppParamsDTO appParamsDTO2 = new AppParamsDTO();
        assertThat(appParamsDTO1).isNotEqualTo(appParamsDTO2);
        appParamsDTO2.setId(appParamsDTO1.getId());
        assertThat(appParamsDTO1).isEqualTo(appParamsDTO2);
        appParamsDTO2.setId(2L);
        assertThat(appParamsDTO1).isNotEqualTo(appParamsDTO2);
        appParamsDTO1.setId(null);
        assertThat(appParamsDTO1).isNotEqualTo(appParamsDTO2);
    }
}
