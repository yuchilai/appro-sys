package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CIPhoneNumTestSamples.*;
import static com.mycompany.myapp.domain.ContactInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CIPhoneNumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIPhoneNum.class);
        CIPhoneNum cIPhoneNum1 = getCIPhoneNumSample1();
        CIPhoneNum cIPhoneNum2 = new CIPhoneNum();
        assertThat(cIPhoneNum1).isNotEqualTo(cIPhoneNum2);

        cIPhoneNum2.setId(cIPhoneNum1.getId());
        assertThat(cIPhoneNum1).isEqualTo(cIPhoneNum2);

        cIPhoneNum2 = getCIPhoneNumSample2();
        assertThat(cIPhoneNum1).isNotEqualTo(cIPhoneNum2);
    }

    @Test
    void contactInfoTest() throws Exception {
        CIPhoneNum cIPhoneNum = getCIPhoneNumRandomSampleGenerator();
        ContactInfo contactInfoBack = getContactInfoRandomSampleGenerator();

        cIPhoneNum.setContactInfo(contactInfoBack);
        assertThat(cIPhoneNum.getContactInfo()).isEqualTo(contactInfoBack);

        cIPhoneNum.contactInfo(null);
        assertThat(cIPhoneNum.getContactInfo()).isNull();
    }
}
