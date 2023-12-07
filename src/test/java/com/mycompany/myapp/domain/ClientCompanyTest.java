package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AccountsPayableTestSamples.*;
import static com.mycompany.myapp.domain.ApproverTestSamples.*;
import static com.mycompany.myapp.domain.CCAddressTestSamples.*;
import static com.mycompany.myapp.domain.CCEmailTestSamples.*;
import static com.mycompany.myapp.domain.CCPhoneNumTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static com.mycompany.myapp.domain.ContactInfoTestSamples.*;
import static com.mycompany.myapp.domain.HourlyRateTestSamples.*;
import static com.mycompany.myapp.domain.ProjectServiceTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientCompany.class);
        ClientCompany clientCompany1 = getClientCompanySample1();
        ClientCompany clientCompany2 = new ClientCompany();
        assertThat(clientCompany1).isNotEqualTo(clientCompany2);

        clientCompany2.setId(clientCompany1.getId());
        assertThat(clientCompany1).isEqualTo(clientCompany2);

        clientCompany2 = getClientCompanySample2();
        assertThat(clientCompany1).isNotEqualTo(clientCompany2);
    }

    @Test
    void hourlyRateTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        HourlyRate hourlyRateBack = getHourlyRateRandomSampleGenerator();

        clientCompany.addHourlyRate(hourlyRateBack);
        assertThat(clientCompany.getHourlyRates()).containsOnly(hourlyRateBack);
        assertThat(hourlyRateBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeHourlyRate(hourlyRateBack);
        assertThat(clientCompany.getHourlyRates()).doesNotContain(hourlyRateBack);
        assertThat(hourlyRateBack.getClientCompany()).isNull();

        clientCompany.hourlyRates(new HashSet<>(Set.of(hourlyRateBack)));
        assertThat(clientCompany.getHourlyRates()).containsOnly(hourlyRateBack);
        assertThat(hourlyRateBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setHourlyRates(new HashSet<>());
        assertThat(clientCompany.getHourlyRates()).doesNotContain(hourlyRateBack);
        assertThat(hourlyRateBack.getClientCompany()).isNull();
    }

    @Test
    void projectServiceTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        ProjectService projectServiceBack = getProjectServiceRandomSampleGenerator();

        clientCompany.addProjectService(projectServiceBack);
        assertThat(clientCompany.getProjectServices()).containsOnly(projectServiceBack);
        assertThat(projectServiceBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeProjectService(projectServiceBack);
        assertThat(clientCompany.getProjectServices()).doesNotContain(projectServiceBack);
        assertThat(projectServiceBack.getClientCompany()).isNull();

        clientCompany.projectServices(new HashSet<>(Set.of(projectServiceBack)));
        assertThat(clientCompany.getProjectServices()).containsOnly(projectServiceBack);
        assertThat(projectServiceBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setProjectServices(new HashSet<>());
        assertThat(clientCompany.getProjectServices()).doesNotContain(projectServiceBack);
        assertThat(projectServiceBack.getClientCompany()).isNull();
    }

    @Test
    void contactInfoTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        ContactInfo contactInfoBack = getContactInfoRandomSampleGenerator();

        clientCompany.addContactInfo(contactInfoBack);
        assertThat(clientCompany.getContactInfos()).containsOnly(contactInfoBack);
        assertThat(contactInfoBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeContactInfo(contactInfoBack);
        assertThat(clientCompany.getContactInfos()).doesNotContain(contactInfoBack);
        assertThat(contactInfoBack.getClientCompany()).isNull();

        clientCompany.contactInfos(new HashSet<>(Set.of(contactInfoBack)));
        assertThat(clientCompany.getContactInfos()).containsOnly(contactInfoBack);
        assertThat(contactInfoBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setContactInfos(new HashSet<>());
        assertThat(clientCompany.getContactInfos()).doesNotContain(contactInfoBack);
        assertThat(contactInfoBack.getClientCompany()).isNull();
    }

    @Test
    void ccEmailTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        CCEmail cCEmailBack = getCCEmailRandomSampleGenerator();

        clientCompany.addCcEmail(cCEmailBack);
        assertThat(clientCompany.getCcEmails()).containsOnly(cCEmailBack);
        assertThat(cCEmailBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeCcEmail(cCEmailBack);
        assertThat(clientCompany.getCcEmails()).doesNotContain(cCEmailBack);
        assertThat(cCEmailBack.getClientCompany()).isNull();

        clientCompany.ccEmails(new HashSet<>(Set.of(cCEmailBack)));
        assertThat(clientCompany.getCcEmails()).containsOnly(cCEmailBack);
        assertThat(cCEmailBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setCcEmails(new HashSet<>());
        assertThat(clientCompany.getCcEmails()).doesNotContain(cCEmailBack);
        assertThat(cCEmailBack.getClientCompany()).isNull();
    }

    @Test
    void ccPhoneNumTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        CCPhoneNum cCPhoneNumBack = getCCPhoneNumRandomSampleGenerator();

        clientCompany.addCcPhoneNum(cCPhoneNumBack);
        assertThat(clientCompany.getCcPhoneNums()).containsOnly(cCPhoneNumBack);
        assertThat(cCPhoneNumBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeCcPhoneNum(cCPhoneNumBack);
        assertThat(clientCompany.getCcPhoneNums()).doesNotContain(cCPhoneNumBack);
        assertThat(cCPhoneNumBack.getClientCompany()).isNull();

        clientCompany.ccPhoneNums(new HashSet<>(Set.of(cCPhoneNumBack)));
        assertThat(clientCompany.getCcPhoneNums()).containsOnly(cCPhoneNumBack);
        assertThat(cCPhoneNumBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setCcPhoneNums(new HashSet<>());
        assertThat(clientCompany.getCcPhoneNums()).doesNotContain(cCPhoneNumBack);
        assertThat(cCPhoneNumBack.getClientCompany()).isNull();
    }

    @Test
    void ccAddressTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        CCAddress cCAddressBack = getCCAddressRandomSampleGenerator();

        clientCompany.addCcAddress(cCAddressBack);
        assertThat(clientCompany.getCcAddresses()).containsOnly(cCAddressBack);
        assertThat(cCAddressBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeCcAddress(cCAddressBack);
        assertThat(clientCompany.getCcAddresses()).doesNotContain(cCAddressBack);
        assertThat(cCAddressBack.getClientCompany()).isNull();

        clientCompany.ccAddresses(new HashSet<>(Set.of(cCAddressBack)));
        assertThat(clientCompany.getCcAddresses()).containsOnly(cCAddressBack);
        assertThat(cCAddressBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setCcAddresses(new HashSet<>());
        assertThat(clientCompany.getCcAddresses()).doesNotContain(cCAddressBack);
        assertThat(cCAddressBack.getClientCompany()).isNull();
    }

    @Test
    void workEntryTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        clientCompany.addWorkEntry(workEntryBack);
        assertThat(clientCompany.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeWorkEntry(workEntryBack);
        assertThat(clientCompany.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getClientCompany()).isNull();

        clientCompany.workEntries(new HashSet<>(Set.of(workEntryBack)));
        assertThat(clientCompany.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setWorkEntries(new HashSet<>());
        assertThat(clientCompany.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getClientCompany()).isNull();
    }

    @Test
    void accountsPayableTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        AccountsPayable accountsPayableBack = getAccountsPayableRandomSampleGenerator();

        clientCompany.addAccountsPayable(accountsPayableBack);
        assertThat(clientCompany.getAccountsPayables()).containsOnly(accountsPayableBack);
        assertThat(accountsPayableBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.removeAccountsPayable(accountsPayableBack);
        assertThat(clientCompany.getAccountsPayables()).doesNotContain(accountsPayableBack);
        assertThat(accountsPayableBack.getClientCompany()).isNull();

        clientCompany.accountsPayables(new HashSet<>(Set.of(accountsPayableBack)));
        assertThat(clientCompany.getAccountsPayables()).containsOnly(accountsPayableBack);
        assertThat(accountsPayableBack.getClientCompany()).isEqualTo(clientCompany);

        clientCompany.setAccountsPayables(new HashSet<>());
        assertThat(clientCompany.getAccountsPayables()).doesNotContain(accountsPayableBack);
        assertThat(accountsPayableBack.getClientCompany()).isNull();
    }

    @Test
    void approverTest() throws Exception {
        ClientCompany clientCompany = getClientCompanyRandomSampleGenerator();
        Approver approverBack = getApproverRandomSampleGenerator();

        clientCompany.addApprover(approverBack);
        assertThat(clientCompany.getApprovers()).containsOnly(approverBack);

        clientCompany.removeApprover(approverBack);
        assertThat(clientCompany.getApprovers()).doesNotContain(approverBack);

        clientCompany.approvers(new HashSet<>(Set.of(approverBack)));
        assertThat(clientCompany.getApprovers()).containsOnly(approverBack);

        clientCompany.setApprovers(new HashSet<>());
        assertThat(clientCompany.getApprovers()).doesNotContain(approverBack);
    }
}
