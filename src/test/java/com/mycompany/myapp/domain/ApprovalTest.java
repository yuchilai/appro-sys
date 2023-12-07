package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApprovalTestSamples.*;
import static com.mycompany.myapp.domain.ApproverTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Approval.class);
        Approval approval1 = getApprovalSample1();
        Approval approval2 = new Approval();
        assertThat(approval1).isNotEqualTo(approval2);

        approval2.setId(approval1.getId());
        assertThat(approval1).isEqualTo(approval2);

        approval2 = getApprovalSample2();
        assertThat(approval1).isNotEqualTo(approval2);
    }

    @Test
    void approverTest() throws Exception {
        Approval approval = getApprovalRandomSampleGenerator();
        Approver approverBack = getApproverRandomSampleGenerator();

        approval.setApprover(approverBack);
        assertThat(approval.getApprover()).isEqualTo(approverBack);

        approval.approver(null);
        assertThat(approval.getApprover()).isNull();
    }

    @Test
    void workEntryTest() throws Exception {
        Approval approval = getApprovalRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        approval.setWorkEntry(workEntryBack);
        assertThat(approval.getWorkEntry()).isEqualTo(workEntryBack);

        approval.workEntry(null);
        assertThat(approval.getWorkEntry()).isNull();
    }
}
