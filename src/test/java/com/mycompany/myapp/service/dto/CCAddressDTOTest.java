package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CCAddressDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCAddressDTO.class);
        CCAddressDTO cCAddressDTO1 = new CCAddressDTO();
        cCAddressDTO1.setId(1L);
        CCAddressDTO cCAddressDTO2 = new CCAddressDTO();
        assertThat(cCAddressDTO1).isNotEqualTo(cCAddressDTO2);
        cCAddressDTO2.setId(cCAddressDTO1.getId());
        assertThat(cCAddressDTO1).isEqualTo(cCAddressDTO2);
        cCAddressDTO2.setId(2L);
        assertThat(cCAddressDTO1).isNotEqualTo(cCAddressDTO2);
        cCAddressDTO1.setId(null);
        assertThat(cCAddressDTO1).isNotEqualTo(cCAddressDTO2);
    }
}
