package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleFunctionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleFunctionsDTO.class);
        RoleFunctionsDTO roleFunctionsDTO1 = new RoleFunctionsDTO();
        roleFunctionsDTO1.setId(1L);
        RoleFunctionsDTO roleFunctionsDTO2 = new RoleFunctionsDTO();
        assertThat(roleFunctionsDTO1).isNotEqualTo(roleFunctionsDTO2);
        roleFunctionsDTO2.setId(roleFunctionsDTO1.getId());
        assertThat(roleFunctionsDTO1).isEqualTo(roleFunctionsDTO2);
        roleFunctionsDTO2.setId(2L);
        assertThat(roleFunctionsDTO1).isNotEqualTo(roleFunctionsDTO2);
        roleFunctionsDTO1.setId(null);
        assertThat(roleFunctionsDTO1).isNotEqualTo(roleFunctionsDTO2);
    }
}
