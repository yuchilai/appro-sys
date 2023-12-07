package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static com.mycompany.myapp.domain.HourlyRateTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HourlyRateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HourlyRate.class);
        HourlyRate hourlyRate1 = getHourlyRateSample1();
        HourlyRate hourlyRate2 = new HourlyRate();
        assertThat(hourlyRate1).isNotEqualTo(hourlyRate2);

        hourlyRate2.setId(hourlyRate1.getId());
        assertThat(hourlyRate1).isEqualTo(hourlyRate2);

        hourlyRate2 = getHourlyRateSample2();
        assertThat(hourlyRate1).isNotEqualTo(hourlyRate2);
    }

    @Test
    void workEntryTest() throws Exception {
        HourlyRate hourlyRate = getHourlyRateRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        hourlyRate.addWorkEntry(workEntryBack);
        assertThat(hourlyRate.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getHourlyRate()).isEqualTo(hourlyRate);

        hourlyRate.removeWorkEntry(workEntryBack);
        assertThat(hourlyRate.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getHourlyRate()).isNull();

        hourlyRate.workEntries(new HashSet<>(Set.of(workEntryBack)));
        assertThat(hourlyRate.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getHourlyRate()).isEqualTo(hourlyRate);

        hourlyRate.setWorkEntries(new HashSet<>());
        assertThat(hourlyRate.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getHourlyRate()).isNull();
    }

    @Test
    void clientCompanyTest() throws Exception {
        HourlyRate hourlyRate = getHourlyRateRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        hourlyRate.setClientCompany(clientCompanyBack);
        assertThat(hourlyRate.getClientCompany()).isEqualTo(clientCompanyBack);

        hourlyRate.clientCompany(null);
        assertThat(hourlyRate.getClientCompany()).isNull();
    }
}
