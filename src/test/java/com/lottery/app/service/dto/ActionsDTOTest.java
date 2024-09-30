package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionsDTO.class);
        ActionsDTO actionsDTO1 = new ActionsDTO();
        actionsDTO1.setId(1L);
        ActionsDTO actionsDTO2 = new ActionsDTO();
        assertThat(actionsDTO1).isNotEqualTo(actionsDTO2);
        actionsDTO2.setId(actionsDTO1.getId());
        assertThat(actionsDTO1).isEqualTo(actionsDTO2);
        actionsDTO2.setId(2L);
        assertThat(actionsDTO1).isNotEqualTo(actionsDTO2);
        actionsDTO1.setId(null);
        assertThat(actionsDTO1).isNotEqualTo(actionsDTO2);
    }
}
