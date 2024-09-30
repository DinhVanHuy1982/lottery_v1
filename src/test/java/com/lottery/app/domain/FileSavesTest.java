package com.lottery.app.domain;

import static com.lottery.app.domain.FileSavesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileSavesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileSaves.class);
        FileSaves fileSaves1 = getFileSavesSample1();
        FileSaves fileSaves2 = new FileSaves();
        assertThat(fileSaves1).isNotEqualTo(fileSaves2);

        fileSaves2.setId(fileSaves1.getId());
        assertThat(fileSaves1).isEqualTo(fileSaves2);

        fileSaves2 = getFileSavesSample2();
        assertThat(fileSaves1).isNotEqualTo(fileSaves2);
    }
}
