package com.lottery.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lottery.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileSavesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileSavesDTO.class);
        FileSavesDTO fileSavesDTO1 = new FileSavesDTO();
        fileSavesDTO1.setId(1L);
        FileSavesDTO fileSavesDTO2 = new FileSavesDTO();
        assertThat(fileSavesDTO1).isNotEqualTo(fileSavesDTO2);
        fileSavesDTO2.setId(fileSavesDTO1.getId());
        assertThat(fileSavesDTO1).isEqualTo(fileSavesDTO2);
        fileSavesDTO2.setId(2L);
        assertThat(fileSavesDTO1).isNotEqualTo(fileSavesDTO2);
        fileSavesDTO1.setId(null);
        assertThat(fileSavesDTO1).isNotEqualTo(fileSavesDTO2);
    }
}
