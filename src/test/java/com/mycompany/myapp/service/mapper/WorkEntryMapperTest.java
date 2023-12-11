package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

class WorkEntryMapperTest {

    // @Autowired
    private WorkEntryMapper workEntryMapper;

    @BeforeEach
    public void setUp() {
        workEntryMapper = new WorkEntryMapperImpl();
    }
}
