package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CIAddressTestSamples.*;
import static com.mycompany.myapp.domain.CIEmailTestSamples.*;
import static com.mycompany.myapp.domain.CIPhoneNumTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static com.mycompany.myapp.domain.ContactInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ContactInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactInfo.class);
        ContactInfo contactInfo1 = getContactInfoSample1();
        ContactInfo contactInfo2 = new ContactInfo();
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);

        contactInfo2.setId(contactInfo1.getId());
        assertThat(contactInfo1).isEqualTo(contactInfo2);

        contactInfo2 = getContactInfoSample2();
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);
    }

    @Test
    void ciEmailTest() throws Exception {
        ContactInfo contactInfo = getContactInfoRandomSampleGenerator();
        CIEmail cIEmailBack = getCIEmailRandomSampleGenerator();

        contactInfo.addCiEmail(cIEmailBack);
        assertThat(contactInfo.getCiEmails()).containsOnly(cIEmailBack);
        assertThat(cIEmailBack.getContactInfo()).isEqualTo(contactInfo);

        contactInfo.removeCiEmail(cIEmailBack);
        assertThat(contactInfo.getCiEmails()).doesNotContain(cIEmailBack);
        assertThat(cIEmailBack.getContactInfo()).isNull();

        contactInfo.ciEmails(new HashSet<>(Set.of(cIEmailBack)));
        assertThat(contactInfo.getCiEmails()).containsOnly(cIEmailBack);
        assertThat(cIEmailBack.getContactInfo()).isEqualTo(contactInfo);

        contactInfo.setCiEmails(new HashSet<>());
        assertThat(contactInfo.getCiEmails()).doesNotContain(cIEmailBack);
        assertThat(cIEmailBack.getContactInfo()).isNull();
    }

    @Test
    void ciPhoneNumTest() throws Exception {
        ContactInfo contactInfo = getContactInfoRandomSampleGenerator();
        CIPhoneNum cIPhoneNumBack = getCIPhoneNumRandomSampleGenerator();

        contactInfo.addCiPhoneNum(cIPhoneNumBack);
        assertThat(contactInfo.getCiPhoneNums()).containsOnly(cIPhoneNumBack);
        assertThat(cIPhoneNumBack.getContactInfo()).isEqualTo(contactInfo);

        contactInfo.removeCiPhoneNum(cIPhoneNumBack);
        assertThat(contactInfo.getCiPhoneNums()).doesNotContain(cIPhoneNumBack);
        assertThat(cIPhoneNumBack.getContactInfo()).isNull();

        contactInfo.ciPhoneNums(new HashSet<>(Set.of(cIPhoneNumBack)));
        assertThat(contactInfo.getCiPhoneNums()).containsOnly(cIPhoneNumBack);
        assertThat(cIPhoneNumBack.getContactInfo()).isEqualTo(contactInfo);

        contactInfo.setCiPhoneNums(new HashSet<>());
        assertThat(contactInfo.getCiPhoneNums()).doesNotContain(cIPhoneNumBack);
        assertThat(cIPhoneNumBack.getContactInfo()).isNull();
    }

    @Test
    void ciAddressTest() throws Exception {
        ContactInfo contactInfo = getContactInfoRandomSampleGenerator();
        CIAddress cIAddressBack = getCIAddressRandomSampleGenerator();

        contactInfo.addCiAddress(cIAddressBack);
        assertThat(contactInfo.getCiAddresses()).containsOnly(cIAddressBack);
        assertThat(cIAddressBack.getContactInfo()).isEqualTo(contactInfo);

        contactInfo.removeCiAddress(cIAddressBack);
        assertThat(contactInfo.getCiAddresses()).doesNotContain(cIAddressBack);
        assertThat(cIAddressBack.getContactInfo()).isNull();

        contactInfo.ciAddresses(new HashSet<>(Set.of(cIAddressBack)));
        assertThat(contactInfo.getCiAddresses()).containsOnly(cIAddressBack);
        assertThat(cIAddressBack.getContactInfo()).isEqualTo(contactInfo);

        contactInfo.setCiAddresses(new HashSet<>());
        assertThat(contactInfo.getCiAddresses()).doesNotContain(cIAddressBack);
        assertThat(cIAddressBack.getContactInfo()).isNull();
    }

    @Test
    void clientCompanyTest() throws Exception {
        ContactInfo contactInfo = getContactInfoRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        contactInfo.setClientCompany(clientCompanyBack);
        assertThat(contactInfo.getClientCompany()).isEqualTo(clientCompanyBack);

        contactInfo.clientCompany(null);
        assertThat(contactInfo.getClientCompany()).isNull();
    }
}
