package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AddressTestSamples.*;
import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.ApproverTestSamples.*;
import static com.mycompany.myapp.domain.EmailTestSamples.*;
import static com.mycompany.myapp.domain.PhoneNumTestSamples.*;
import static com.mycompany.myapp.domain.TagTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUser.class);
        ApplicationUser applicationUser1 = getApplicationUserSample1();
        ApplicationUser applicationUser2 = new ApplicationUser();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);

        applicationUser2.setId(applicationUser1.getId());
        assertThat(applicationUser1).isEqualTo(applicationUser2);

        applicationUser2 = getApplicationUserSample2();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);
    }

    @Test
    void ownedWorkEntriesTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        applicationUser.addOwnedWorkEntries(workEntryBack);
        assertThat(applicationUser.getOwnedWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getOwner()).isEqualTo(applicationUser);

        applicationUser.removeOwnedWorkEntries(workEntryBack);
        assertThat(applicationUser.getOwnedWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getOwner()).isNull();

        applicationUser.ownedWorkEntries(new HashSet<>(Set.of(workEntryBack)));
        assertThat(applicationUser.getOwnedWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getOwner()).isEqualTo(applicationUser);

        applicationUser.setOwnedWorkEntries(new HashSet<>());
        assertThat(applicationUser.getOwnedWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getOwner()).isNull();
    }

    @Test
    void emailTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        applicationUser.addEmail(emailBack);
        assertThat(applicationUser.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.removeEmail(emailBack);
        assertThat(applicationUser.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getApplicationUser()).isNull();

        applicationUser.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(applicationUser.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.setEmails(new HashSet<>());
        assertThat(applicationUser.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getApplicationUser()).isNull();
    }

    @Test
    void phoneNumTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        PhoneNum phoneNumBack = getPhoneNumRandomSampleGenerator();

        applicationUser.addPhoneNum(phoneNumBack);
        assertThat(applicationUser.getPhoneNums()).containsOnly(phoneNumBack);
        assertThat(phoneNumBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.removePhoneNum(phoneNumBack);
        assertThat(applicationUser.getPhoneNums()).doesNotContain(phoneNumBack);
        assertThat(phoneNumBack.getApplicationUser()).isNull();

        applicationUser.phoneNums(new HashSet<>(Set.of(phoneNumBack)));
        assertThat(applicationUser.getPhoneNums()).containsOnly(phoneNumBack);
        assertThat(phoneNumBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.setPhoneNums(new HashSet<>());
        assertThat(applicationUser.getPhoneNums()).doesNotContain(phoneNumBack);
        assertThat(phoneNumBack.getApplicationUser()).isNull();
    }

    @Test
    void addressTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        applicationUser.addAddress(addressBack);
        assertThat(applicationUser.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.removeAddress(addressBack);
        assertThat(applicationUser.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getApplicationUser()).isNull();

        applicationUser.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(applicationUser.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.setAddresses(new HashSet<>());
        assertThat(applicationUser.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getApplicationUser()).isNull();
    }

    @Test
    void tagTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        applicationUser.addTag(tagBack);
        assertThat(applicationUser.getTags()).containsOnly(tagBack);
        assertThat(tagBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.removeTag(tagBack);
        assertThat(applicationUser.getTags()).doesNotContain(tagBack);
        assertThat(tagBack.getApplicationUser()).isNull();

        applicationUser.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(applicationUser.getTags()).containsOnly(tagBack);
        assertThat(tagBack.getApplicationUser()).isEqualTo(applicationUser);

        applicationUser.setTags(new HashSet<>());
        assertThat(applicationUser.getTags()).doesNotContain(tagBack);
        assertThat(tagBack.getApplicationUser()).isNull();
    }

    @Test
    void approverTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Approver approverBack = getApproverRandomSampleGenerator();

        applicationUser.setApprover(approverBack);
        assertThat(applicationUser.getApprover()).isEqualTo(approverBack);

        applicationUser.approver(null);
        assertThat(applicationUser.getApprover()).isNull();
    }
}
