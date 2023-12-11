package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

class ApproverMapperTest {

    // @Autowired
    private ApproverMapper approverMapper;

    @BeforeEach
    public void setUp() {
        approverMapper = new ApproverMapperImpl();
    }
}
