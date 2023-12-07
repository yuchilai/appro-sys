package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ClientCompanyMapperTest {

    private ClientCompanyMapper clientCompanyMapper;

    @BeforeEach
    public void setUp() {
        clientCompanyMapper = new ClientCompanyMapperImpl();
    }
}
