package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientCompanyDTO.class);
        ClientCompanyDTO clientCompanyDTO1 = new ClientCompanyDTO();
        clientCompanyDTO1.setId(1L);
        ClientCompanyDTO clientCompanyDTO2 = new ClientCompanyDTO();
        assertThat(clientCompanyDTO1).isNotEqualTo(clientCompanyDTO2);
        clientCompanyDTO2.setId(clientCompanyDTO1.getId());
        assertThat(clientCompanyDTO1).isEqualTo(clientCompanyDTO2);
        clientCompanyDTO2.setId(2L);
        assertThat(clientCompanyDTO1).isNotEqualTo(clientCompanyDTO2);
        clientCompanyDTO1.setId(null);
        assertThat(clientCompanyDTO1).isNotEqualTo(clientCompanyDTO2);
    }
}
