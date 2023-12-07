package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class HourlyRateMapperTest {

    private HourlyRateMapper hourlyRateMapper;

    @BeforeEach
    public void setUp() {
        hourlyRateMapper = new HourlyRateMapperImpl();
    }
}
