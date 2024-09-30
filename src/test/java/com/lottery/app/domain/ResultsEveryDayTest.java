package com.lottery.app.domain;

import static com.lottery.app.domain.ResultsEveryDayTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultsEveryDayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultsEveryDay.class);
        ResultsEveryDay resultsEveryDay1 = getResultsEveryDaySample1();
        ResultsEveryDay resultsEveryDay2 = new ResultsEveryDay();
        assertThat(resultsEveryDay1).isNotEqualTo(resultsEveryDay2);

        resultsEveryDay2.setId(resultsEveryDay1.getId());
        assertThat(resultsEveryDay1).isEqualTo(resultsEveryDay2);

        resultsEveryDay2 = getResultsEveryDaySample2();
        assertThat(resultsEveryDay1).isNotEqualTo(resultsEveryDay2);
    }
}
