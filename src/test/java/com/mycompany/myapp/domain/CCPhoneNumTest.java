package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CCPhoneNumTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CCPhoneNumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCPhoneNum.class);
        CCPhoneNum cCPhoneNum1 = getCCPhoneNumSample1();
        CCPhoneNum cCPhoneNum2 = new CCPhoneNum();
        assertThat(cCPhoneNum1).isNotEqualTo(cCPhoneNum2);

        cCPhoneNum2.setId(cCPhoneNum1.getId());
        assertThat(cCPhoneNum1).isEqualTo(cCPhoneNum2);

        cCPhoneNum2 = getCCPhoneNumSample2();
        assertThat(cCPhoneNum1).isNotEqualTo(cCPhoneNum2);
    }

    @Test
    void clientCompanyTest() throws Exception {
        CCPhoneNum cCPhoneNum = getCCPhoneNumRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        cCPhoneNum.setClientCompany(clientCompanyBack);
        assertThat(cCPhoneNum.getClientCompany()).isEqualTo(clientCompanyBack);

        cCPhoneNum.clientCompany(null);
        assertThat(cCPhoneNum.getClientCompany()).isNull();
    }
}
