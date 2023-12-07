package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ContactInfoMapperTest {

    private ContactInfoMapper contactInfoMapper;

    @BeforeEach
    public void setUp() {
        contactInfoMapper = new ContactInfoMapperImpl();
    }
}
