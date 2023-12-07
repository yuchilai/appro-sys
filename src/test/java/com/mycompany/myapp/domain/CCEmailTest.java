package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CCEmailTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CCEmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCEmail.class);
        CCEmail cCEmail1 = getCCEmailSample1();
        CCEmail cCEmail2 = new CCEmail();
        assertThat(cCEmail1).isNotEqualTo(cCEmail2);

        cCEmail2.setId(cCEmail1.getId());
        assertThat(cCEmail1).isEqualTo(cCEmail2);

        cCEmail2 = getCCEmailSample2();
        assertThat(cCEmail1).isNotEqualTo(cCEmail2);
    }

    @Test
    void clientCompanyTest() throws Exception {
        CCEmail cCEmail = getCCEmailRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        cCEmail.setClientCompany(clientCompanyBack);
        assertThat(cCEmail.getClientCompany()).isEqualTo(clientCompanyBack);

        cCEmail.clientCompany(null);
        assertThat(cCEmail.getClientCompany()).isNull();
    }
}
