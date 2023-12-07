package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.ApprovalTestSamples.*;
import static com.mycompany.myapp.domain.ApproverTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApproverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Approver.class);
        Approver approver1 = getApproverSample1();
        Approver approver2 = new Approver();
        assertThat(approver1).isNotEqualTo(approver2);

        approver2.setId(approver1.getId());
        assertThat(approver1).isEqualTo(approver2);

        approver2 = getApproverSample2();
        assertThat(approver1).isNotEqualTo(approver2);
    }

    @Test
    void approvalTest() throws Exception {
        Approver approver = getApproverRandomSampleGenerator();
        Approval approvalBack = getApprovalRandomSampleGenerator();

        approver.addApproval(approvalBack);
        assertThat(approver.getApprovals()).containsOnly(approvalBack);
        assertThat(approvalBack.getApprover()).isEqualTo(approver);

        approver.removeApproval(approvalBack);
        assertThat(approver.getApprovals()).doesNotContain(approvalBack);
        assertThat(approvalBack.getApprover()).isNull();

        approver.approvals(new HashSet<>(Set.of(approvalBack)));
        assertThat(approver.getApprovals()).containsOnly(approvalBack);
        assertThat(approvalBack.getApprover()).isEqualTo(approver);

        approver.setApprovals(new HashSet<>());
        assertThat(approver.getApprovals()).doesNotContain(approvalBack);
        assertThat(approvalBack.getApprover()).isNull();
    }

    @Test
    void approvedWorkEntriesTest() throws Exception {
        Approver approver = getApproverRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        approver.addApprovedWorkEntries(workEntryBack);
        assertThat(approver.getApprovedWorkEntries()).containsOnly(workEntryBack);

        approver.removeApprovedWorkEntries(workEntryBack);
        assertThat(approver.getApprovedWorkEntries()).doesNotContain(workEntryBack);

        approver.approvedWorkEntries(new HashSet<>(Set.of(workEntryBack)));
        assertThat(approver.getApprovedWorkEntries()).containsOnly(workEntryBack);

        approver.setApprovedWorkEntries(new HashSet<>());
        assertThat(approver.getApprovedWorkEntries()).doesNotContain(workEntryBack);
    }

    @Test
    void applicationUserTest() throws Exception {
        Approver approver = getApproverRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        approver.addApplicationUser(applicationUserBack);
        assertThat(approver.getApplicationUsers()).containsOnly(applicationUserBack);
        assertThat(applicationUserBack.getApprover()).isEqualTo(approver);

        approver.removeApplicationUser(applicationUserBack);
        assertThat(approver.getApplicationUsers()).doesNotContain(applicationUserBack);
        assertThat(applicationUserBack.getApprover()).isNull();

        approver.applicationUsers(new HashSet<>(Set.of(applicationUserBack)));
        assertThat(approver.getApplicationUsers()).containsOnly(applicationUserBack);
        assertThat(applicationUserBack.getApprover()).isEqualTo(approver);

        approver.setApplicationUsers(new HashSet<>());
        assertThat(approver.getApplicationUsers()).doesNotContain(applicationUserBack);
        assertThat(applicationUserBack.getApprover()).isNull();
    }

    @Test
    void clientCompanyTest() throws Exception {
        Approver approver = getApproverRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        approver.addClientCompany(clientCompanyBack);
        assertThat(approver.getClientCompanies()).containsOnly(clientCompanyBack);
        assertThat(clientCompanyBack.getApprovers()).containsOnly(approver);

        approver.removeClientCompany(clientCompanyBack);
        assertThat(approver.getClientCompanies()).doesNotContain(clientCompanyBack);
        assertThat(clientCompanyBack.getApprovers()).doesNotContain(approver);

        approver.clientCompanies(new HashSet<>(Set.of(clientCompanyBack)));
        assertThat(approver.getClientCompanies()).containsOnly(clientCompanyBack);
        assertThat(clientCompanyBack.getApprovers()).containsOnly(approver);

        approver.setClientCompanies(new HashSet<>());
        assertThat(approver.getClientCompanies()).doesNotContain(clientCompanyBack);
        assertThat(clientCompanyBack.getApprovers()).doesNotContain(approver);
    }
}
