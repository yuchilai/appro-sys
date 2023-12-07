package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkEntryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEntryDTO.class);
        WorkEntryDTO workEntryDTO1 = new WorkEntryDTO();
        workEntryDTO1.setId(1L);
        WorkEntryDTO workEntryDTO2 = new WorkEntryDTO();
        assertThat(workEntryDTO1).isNotEqualTo(workEntryDTO2);
        workEntryDTO2.setId(workEntryDTO1.getId());
        assertThat(workEntryDTO1).isEqualTo(workEntryDTO2);
        workEntryDTO2.setId(2L);
        assertThat(workEntryDTO1).isNotEqualTo(workEntryDTO2);
        workEntryDTO1.setId(null);
        assertThat(workEntryDTO1).isNotEqualTo(workEntryDTO2);
    }
}
