package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CCPhoneNumDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCPhoneNumDTO.class);
        CCPhoneNumDTO cCPhoneNumDTO1 = new CCPhoneNumDTO();
        cCPhoneNumDTO1.setId(1L);
        CCPhoneNumDTO cCPhoneNumDTO2 = new CCPhoneNumDTO();
        assertThat(cCPhoneNumDTO1).isNotEqualTo(cCPhoneNumDTO2);
        cCPhoneNumDTO2.setId(cCPhoneNumDTO1.getId());
        assertThat(cCPhoneNumDTO1).isEqualTo(cCPhoneNumDTO2);
        cCPhoneNumDTO2.setId(2L);
        assertThat(cCPhoneNumDTO1).isNotEqualTo(cCPhoneNumDTO2);
        cCPhoneNumDTO1.setId(null);
        assertThat(cCPhoneNumDTO1).isNotEqualTo(cCPhoneNumDTO2);
    }
}
