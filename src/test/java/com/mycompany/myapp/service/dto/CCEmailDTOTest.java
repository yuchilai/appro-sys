package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CCEmailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCEmailDTO.class);
        CCEmailDTO cCEmailDTO1 = new CCEmailDTO();
        cCEmailDTO1.setId(1L);
        CCEmailDTO cCEmailDTO2 = new CCEmailDTO();
        assertThat(cCEmailDTO1).isNotEqualTo(cCEmailDTO2);
        cCEmailDTO2.setId(cCEmailDTO1.getId());
        assertThat(cCEmailDTO1).isEqualTo(cCEmailDTO2);
        cCEmailDTO2.setId(2L);
        assertThat(cCEmailDTO1).isNotEqualTo(cCEmailDTO2);
        cCEmailDTO1.setId(null);
        assertThat(cCEmailDTO1).isNotEqualTo(cCEmailDTO2);
    }
}
