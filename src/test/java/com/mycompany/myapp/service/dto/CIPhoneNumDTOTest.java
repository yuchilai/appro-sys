package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CIPhoneNumDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIPhoneNumDTO.class);
        CIPhoneNumDTO cIPhoneNumDTO1 = new CIPhoneNumDTO();
        cIPhoneNumDTO1.setId(1L);
        CIPhoneNumDTO cIPhoneNumDTO2 = new CIPhoneNumDTO();
        assertThat(cIPhoneNumDTO1).isNotEqualTo(cIPhoneNumDTO2);
        cIPhoneNumDTO2.setId(cIPhoneNumDTO1.getId());
        assertThat(cIPhoneNumDTO1).isEqualTo(cIPhoneNumDTO2);
        cIPhoneNumDTO2.setId(2L);
        assertThat(cIPhoneNumDTO1).isNotEqualTo(cIPhoneNumDTO2);
        cIPhoneNumDTO1.setId(null);
        assertThat(cIPhoneNumDTO1).isNotEqualTo(cIPhoneNumDTO2);
    }
}
