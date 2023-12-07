package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhoneNumDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumDTO.class);
        PhoneNumDTO phoneNumDTO1 = new PhoneNumDTO();
        phoneNumDTO1.setId(1L);
        PhoneNumDTO phoneNumDTO2 = new PhoneNumDTO();
        assertThat(phoneNumDTO1).isNotEqualTo(phoneNumDTO2);
        phoneNumDTO2.setId(phoneNumDTO1.getId());
        assertThat(phoneNumDTO1).isEqualTo(phoneNumDTO2);
        phoneNumDTO2.setId(2L);
        assertThat(phoneNumDTO1).isNotEqualTo(phoneNumDTO2);
        phoneNumDTO1.setId(null);
        assertThat(phoneNumDTO1).isNotEqualTo(phoneNumDTO2);
    }
}
