package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectServiceDTO.class);
        ProjectServiceDTO projectServiceDTO1 = new ProjectServiceDTO();
        projectServiceDTO1.setId(1L);
        ProjectServiceDTO projectServiceDTO2 = new ProjectServiceDTO();
        assertThat(projectServiceDTO1).isNotEqualTo(projectServiceDTO2);
        projectServiceDTO2.setId(projectServiceDTO1.getId());
        assertThat(projectServiceDTO1).isEqualTo(projectServiceDTO2);
        projectServiceDTO2.setId(2L);
        assertThat(projectServiceDTO1).isNotEqualTo(projectServiceDTO2);
        projectServiceDTO1.setId(null);
        assertThat(projectServiceDTO1).isNotEqualTo(projectServiceDTO2);
    }
}
