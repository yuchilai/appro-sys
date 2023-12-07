package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CCAddressTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CCAddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCAddress.class);
        CCAddress cCAddress1 = getCCAddressSample1();
        CCAddress cCAddress2 = new CCAddress();
        assertThat(cCAddress1).isNotEqualTo(cCAddress2);

        cCAddress2.setId(cCAddress1.getId());
        assertThat(cCAddress1).isEqualTo(cCAddress2);

        cCAddress2 = getCCAddressSample2();
        assertThat(cCAddress1).isNotEqualTo(cCAddress2);
    }

    @Test
    void clientCompanyTest() throws Exception {
        CCAddress cCAddress = getCCAddressRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        cCAddress.setClientCompany(clientCompanyBack);
        assertThat(cCAddress.getClientCompany()).isEqualTo(clientCompanyBack);

        cCAddress.clientCompany(null);
        assertThat(cCAddress.getClientCompany()).isNull();
    }
}
