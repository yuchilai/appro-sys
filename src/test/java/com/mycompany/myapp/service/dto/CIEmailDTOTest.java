package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CIEmailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIEmailDTO.class);
        CIEmailDTO cIEmailDTO1 = new CIEmailDTO();
        cIEmailDTO1.setId(1L);
        CIEmailDTO cIEmailDTO2 = new CIEmailDTO();
        assertThat(cIEmailDTO1).isNotEqualTo(cIEmailDTO2);
        cIEmailDTO2.setId(cIEmailDTO1.getId());
        assertThat(cIEmailDTO1).isEqualTo(cIEmailDTO2);
        cIEmailDTO2.setId(2L);
        assertThat(cIEmailDTO1).isNotEqualTo(cIEmailDTO2);
        cIEmailDTO1.setId(null);
        assertThat(cIEmailDTO1).isNotEqualTo(cIEmailDTO2);
    }
}
