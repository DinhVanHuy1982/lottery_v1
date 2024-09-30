package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultsEveryDayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultsEveryDayDTO.class);
        ResultsEveryDayDTO resultsEveryDayDTO1 = new ResultsEveryDayDTO();
        resultsEveryDayDTO1.setId(1L);
        ResultsEveryDayDTO resultsEveryDayDTO2 = new ResultsEveryDayDTO();
        assertThat(resultsEveryDayDTO1).isNotEqualTo(resultsEveryDayDTO2);
        resultsEveryDayDTO2.setId(resultsEveryDayDTO1.getId());
        assertThat(resultsEveryDayDTO1).isEqualTo(resultsEveryDayDTO2);
        resultsEveryDayDTO2.setId(2L);
        assertThat(resultsEveryDayDTO1).isNotEqualTo(resultsEveryDayDTO2);
        resultsEveryDayDTO1.setId(null);
        assertThat(resultsEveryDayDTO1).isNotEqualTo(resultsEveryDayDTO2);
    }
}
