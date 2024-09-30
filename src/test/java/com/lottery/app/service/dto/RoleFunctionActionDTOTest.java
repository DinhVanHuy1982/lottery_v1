package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleFunctionActionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleFunctionActionDTO.class);
        RoleFunctionActionDTO roleFunctionActionDTO1 = new RoleFunctionActionDTO();
        roleFunctionActionDTO1.setId(1L);
        RoleFunctionActionDTO roleFunctionActionDTO2 = new RoleFunctionActionDTO();
        assertThat(roleFunctionActionDTO1).isNotEqualTo(roleFunctionActionDTO2);
        roleFunctionActionDTO2.setId(roleFunctionActionDTO1.getId());
        assertThat(roleFunctionActionDTO1).isEqualTo(roleFunctionActionDTO2);
        roleFunctionActionDTO2.setId(2L);
        assertThat(roleFunctionActionDTO1).isNotEqualTo(roleFunctionActionDTO2);
        roleFunctionActionDTO1.setId(null);
        assertThat(roleFunctionActionDTO1).isNotEqualTo(roleFunctionActionDTO2);
    }
}
