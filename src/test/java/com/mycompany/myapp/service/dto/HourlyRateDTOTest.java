package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HourlyRateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HourlyRateDTO.class);
        HourlyRateDTO hourlyRateDTO1 = new HourlyRateDTO();
        hourlyRateDTO1.setId(1L);
        HourlyRateDTO hourlyRateDTO2 = new HourlyRateDTO();
        assertThat(hourlyRateDTO1).isNotEqualTo(hourlyRateDTO2);
        hourlyRateDTO2.setId(hourlyRateDTO1.getId());
        assertThat(hourlyRateDTO1).isEqualTo(hourlyRateDTO2);
        hourlyRateDTO2.setId(2L);
        assertThat(hourlyRateDTO1).isNotEqualTo(hourlyRateDTO2);
        hourlyRateDTO1.setId(null);
        assertThat(hourlyRateDTO1).isNotEqualTo(hourlyRateDTO2);
    }
}
