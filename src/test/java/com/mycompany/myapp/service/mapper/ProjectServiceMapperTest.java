package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ProjectServiceMapperTest {

    private ProjectServiceMapper projectServiceMapper;

    @BeforeEach
    public void setUp() {
        projectServiceMapper = new ProjectServiceMapperImpl();
    }
}
