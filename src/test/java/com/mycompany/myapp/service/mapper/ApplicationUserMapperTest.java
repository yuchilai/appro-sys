package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ApplicationUserMapperTest {

    private ApplicationUserMapper applicationUserMapper;

    @BeforeEach
    public void setUp() {
        applicationUserMapper = new ApplicationUserMapperImpl();
    }
}
