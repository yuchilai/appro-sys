package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CIAddressDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIAddressDTO.class);
        CIAddressDTO cIAddressDTO1 = new CIAddressDTO();
        cIAddressDTO1.setId(1L);
        CIAddressDTO cIAddressDTO2 = new CIAddressDTO();
        assertThat(cIAddressDTO1).isNotEqualTo(cIAddressDTO2);
        cIAddressDTO2.setId(cIAddressDTO1.getId());
        assertThat(cIAddressDTO1).isEqualTo(cIAddressDTO2);
        cIAddressDTO2.setId(2L);
        assertThat(cIAddressDTO1).isNotEqualTo(cIAddressDTO2);
        cIAddressDTO1.setId(null);
        assertThat(cIAddressDTO1).isNotEqualTo(cIAddressDTO2);
    }
}
