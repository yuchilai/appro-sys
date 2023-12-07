package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CIEmailTestSamples.*;
import static com.mycompany.myapp.domain.ContactInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CIEmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIEmail.class);
        CIEmail cIEmail1 = getCIEmailSample1();
        CIEmail cIEmail2 = new CIEmail();
        assertThat(cIEmail1).isNotEqualTo(cIEmail2);

        cIEmail2.setId(cIEmail1.getId());
        assertThat(cIEmail1).isEqualTo(cIEmail2);

        cIEmail2 = getCIEmailSample2();
        assertThat(cIEmail1).isNotEqualTo(cIEmail2);
    }

    @Test
    void contactInfoTest() throws Exception {
        CIEmail cIEmail = getCIEmailRandomSampleGenerator();
        ContactInfo contactInfoBack = getContactInfoRandomSampleGenerator();

        cIEmail.setContactInfo(contactInfoBack);
        assertThat(cIEmail.getContactInfo()).isEqualTo(contactInfoBack);

        cIEmail.contactInfo(null);
        assertThat(cIEmail.getContactInfo()).isNull();
    }
}
