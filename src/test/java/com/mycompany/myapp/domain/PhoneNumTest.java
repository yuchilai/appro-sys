package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ApplicationUserTestSamples.*;
import static com.mycompany.myapp.domain.PhoneNumTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhoneNumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNum.class);
        PhoneNum phoneNum1 = getPhoneNumSample1();
        PhoneNum phoneNum2 = new PhoneNum();
        assertThat(phoneNum1).isNotEqualTo(phoneNum2);

        phoneNum2.setId(phoneNum1.getId());
        assertThat(phoneNum1).isEqualTo(phoneNum2);

        phoneNum2 = getPhoneNumSample2();
        assertThat(phoneNum1).isNotEqualTo(phoneNum2);
    }

    @Test
    void applicationUserTest() throws Exception {
        PhoneNum phoneNum = getPhoneNumRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        phoneNum.setApplicationUser(applicationUserBack);
        assertThat(phoneNum.getApplicationUser()).isEqualTo(applicationUserBack);

        phoneNum.applicationUser(null);
        assertThat(phoneNum.getApplicationUser()).isNull();
    }
}
