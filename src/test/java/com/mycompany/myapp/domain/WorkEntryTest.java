package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.ApprovalTestSamples.*;
import static com.mycompany.myapp.domain.ApproverTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static com.mycompany.myapp.domain.HourlyRateTestSamples.*;
import static com.mycompany.myapp.domain.InvoiceTestSamples.*;
import static com.mycompany.myapp.domain.ProjectServiceTestSamples.*;
import static com.mycompany.myapp.domain.TagTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WorkEntryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEntry.class);
        WorkEntry workEntry1 = getWorkEntrySample1();
        WorkEntry workEntry2 = new WorkEntry();
        assertThat(workEntry1).isNotEqualTo(workEntry2);

        workEntry2.setId(workEntry1.getId());
        assertThat(workEntry1).isEqualTo(workEntry2);

        workEntry2 = getWorkEntrySample2();
        assertThat(workEntry1).isNotEqualTo(workEntry2);
    }

    @Test
    void approvalTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        Approval approvalBack = getApprovalRandomSampleGenerator();

        workEntry.addApproval(approvalBack);
        assertThat(workEntry.getApprovals()).containsOnly(approvalBack);
        assertThat(approvalBack.getWorkEntry()).isEqualTo(workEntry);

        workEntry.removeApproval(approvalBack);
        assertThat(workEntry.getApprovals()).doesNotContain(approvalBack);
        assertThat(approvalBack.getWorkEntry()).isNull();

        workEntry.approvals(new HashSet<>(Set.of(approvalBack)));
        assertThat(workEntry.getApprovals()).containsOnly(approvalBack);
        assertThat(approvalBack.getWorkEntry()).isEqualTo(workEntry);

        workEntry.setApprovals(new HashSet<>());
        assertThat(workEntry.getApprovals()).doesNotContain(approvalBack);
        assertThat(approvalBack.getWorkEntry()).isNull();
    }

    @Test
    void hourlyRateTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        HourlyRate hourlyRateBack = getHourlyRateRandomSampleGenerator();

        workEntry.setHourlyRate(hourlyRateBack);
        assertThat(workEntry.getHourlyRate()).isEqualTo(hourlyRateBack);

        workEntry.hourlyRate(null);
        assertThat(workEntry.getHourlyRate()).isNull();
    }

    @Test
    void projectServiceTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        ProjectService projectServiceBack = getProjectServiceRandomSampleGenerator();

        workEntry.setProjectService(projectServiceBack);
        assertThat(workEntry.getProjectService()).isEqualTo(projectServiceBack);

        workEntry.projectService(null);
        assertThat(workEntry.getProjectService()).isNull();
    }

    @Test
    void ownerTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        workEntry.setOwner(applicationUserBack);
        assertThat(workEntry.getOwner()).isEqualTo(applicationUserBack);

        workEntry.owner(null);
        assertThat(workEntry.getOwner()).isNull();
    }

    @Test
    void invoiceTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        workEntry.setInvoice(invoiceBack);
        assertThat(workEntry.getInvoice()).isEqualTo(invoiceBack);

        workEntry.invoice(null);
        assertThat(workEntry.getInvoice()).isNull();
    }

    @Test
    void clientCompanyTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        workEntry.setClientCompany(clientCompanyBack);
        assertThat(workEntry.getClientCompany()).isEqualTo(clientCompanyBack);

        workEntry.clientCompany(null);
        assertThat(workEntry.getClientCompany()).isNull();
    }

    @Test
    void approverTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        Approver approverBack = getApproverRandomSampleGenerator();

        workEntry.addApprover(approverBack);
        assertThat(workEntry.getApprovers()).containsOnly(approverBack);
        assertThat(approverBack.getApprovedWorkEntries()).containsOnly(workEntry);

        workEntry.removeApprover(approverBack);
        assertThat(workEntry.getApprovers()).doesNotContain(approverBack);
        assertThat(approverBack.getApprovedWorkEntries()).doesNotContain(workEntry);

        workEntry.approvers(new HashSet<>(Set.of(approverBack)));
        assertThat(workEntry.getApprovers()).containsOnly(approverBack);
        assertThat(approverBack.getApprovedWorkEntries()).containsOnly(workEntry);

        workEntry.setApprovers(new HashSet<>());
        assertThat(workEntry.getApprovers()).doesNotContain(approverBack);
        assertThat(approverBack.getApprovedWorkEntries()).doesNotContain(workEntry);
    }

    @Test
    void tagTest() throws Exception {
        WorkEntry workEntry = getWorkEntryRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        workEntry.addTag(tagBack);
        assertThat(workEntry.getTags()).containsOnly(tagBack);
        assertThat(tagBack.getWorkEntries()).containsOnly(workEntry);

        workEntry.removeTag(tagBack);
        assertThat(workEntry.getTags()).doesNotContain(tagBack);
        assertThat(tagBack.getWorkEntries()).doesNotContain(workEntry);

        workEntry.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(workEntry.getTags()).containsOnly(tagBack);
        assertThat(tagBack.getWorkEntries()).containsOnly(workEntry);

        workEntry.setTags(new HashSet<>());
        assertThat(workEntry.getTags()).doesNotContain(tagBack);
        assertThat(tagBack.getWorkEntries()).doesNotContain(workEntry);
    }
}
