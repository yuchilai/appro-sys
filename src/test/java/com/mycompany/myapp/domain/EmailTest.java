package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.EmailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Email.class);
        Email email1 = getEmailSample1();
        Email email2 = new Email();
        assertThat(email1).isNotEqualTo(email2);

        email2.setId(email1.getId());
        assertThat(email1).isEqualTo(email2);

        email2 = getEmailSample2();
        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    void applicationUserTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        email.setApplicationUser(applicationUserBack);
        assertThat(email.getApplicationUser()).isEqualTo(applicationUserBack);

        email.applicationUser(null);
        assertThat(email.getApplicationUser()).isNull();
    }
}
