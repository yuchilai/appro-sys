package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static com.mycompany.myapp.domain.ProjectServiceTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProjectServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectService.class);
        ProjectService projectService1 = getProjectServiceSample1();
        ProjectService projectService2 = new ProjectService();
        assertThat(projectService1).isNotEqualTo(projectService2);

        projectService2.setId(projectService1.getId());
        assertThat(projectService1).isEqualTo(projectService2);

        projectService2 = getProjectServiceSample2();
        assertThat(projectService1).isNotEqualTo(projectService2);
    }

    @Test
    void workEntryTest() throws Exception {
        ProjectService projectService = getProjectServiceRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        projectService.addWorkEntry(workEntryBack);
        assertThat(projectService.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getProjectService()).isEqualTo(projectService);

        projectService.removeWorkEntry(workEntryBack);
        assertThat(projectService.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getProjectService()).isNull();

        projectService.workEntries(new HashSet<>(Set.of(workEntryBack)));
        assertThat(projectService.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getProjectService()).isEqualTo(projectService);

        projectService.setWorkEntries(new HashSet<>());
        assertThat(projectService.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getProjectService()).isNull();
    }

    @Test
    void clientCompanyTest() throws Exception {
        ProjectService projectService = getProjectServiceRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        projectService.setClientCompany(clientCompanyBack);
        assertThat(projectService.getClientCompany()).isEqualTo(clientCompanyBack);

        projectService.clientCompany(null);
        assertThat(projectService.getClientCompany()).isNull();
    }
}
