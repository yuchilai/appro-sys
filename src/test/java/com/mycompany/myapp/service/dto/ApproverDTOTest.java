package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApproverDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApproverDTO.class);
        ApproverDTO approverDTO1 = new ApproverDTO();
        approverDTO1.setId(1L);
        ApproverDTO approverDTO2 = new ApproverDTO();
        assertThat(approverDTO1).isNotEqualTo(approverDTO2);
        approverDTO2.setId(approverDTO1.getId());
        assertThat(approverDTO1).isEqualTo(approverDTO2);
        approverDTO2.setId(2L);
        assertThat(approverDTO1).isNotEqualTo(approverDTO2);
        approverDTO1.setId(null);
        assertThat(approverDTO1).isNotEqualTo(approverDTO2);
    }
}
