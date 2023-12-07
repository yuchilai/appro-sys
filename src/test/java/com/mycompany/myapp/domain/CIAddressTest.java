package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CIAddressTestSamples.*;
import static com.mycompany.myapp.domain.ContactInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CIAddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIAddress.class);
        CIAddress cIAddress1 = getCIAddressSample1();
        CIAddress cIAddress2 = new CIAddress();
        assertThat(cIAddress1).isNotEqualTo(cIAddress2);

        cIAddress2.setId(cIAddress1.getId());
        assertThat(cIAddress1).isEqualTo(cIAddress2);

        cIAddress2 = getCIAddressSample2();
        assertThat(cIAddress1).isNotEqualTo(cIAddress2);
    }

    @Test
    void contactInfoTest() throws Exception {
        CIAddress cIAddress = getCIAddressRandomSampleGenerator();
        ContactInfo contactInfoBack = getContactInfoRandomSampleGenerator();

        cIAddress.setContactInfo(contactInfoBack);
        assertThat(cIAddress.getContactInfo()).isEqualTo(contactInfoBack);

        cIAddress.contactInfo(null);
        assertThat(cIAddress.getContactInfo()).isNull();
    }
}
