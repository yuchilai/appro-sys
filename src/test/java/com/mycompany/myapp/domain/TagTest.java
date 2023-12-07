package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.TagTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void workEntryTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        tag.addWorkEntry(workEntryBack);
        assertThat(tag.getWorkEntries()).containsOnly(workEntryBack);

        tag.removeWorkEntry(workEntryBack);
        assertThat(tag.getWorkEntries()).doesNotContain(workEntryBack);

        tag.workEntries(new HashSet<>(Set.of(workEntryBack)));
        assertThat(tag.getWorkEntries()).containsOnly(workEntryBack);

        tag.setWorkEntries(new HashSet<>());
        assertThat(tag.getWorkEntries()).doesNotContain(workEntryBack);
    }

    @Test
    void applicationUserTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        tag.setApplicationUser(applicationUserBack);
        assertThat(tag.getApplicationUser()).isEqualTo(applicationUserBack);

        tag.applicationUser(null);
        assertThat(tag.getApplicationUser()).isNull();
    }
}
