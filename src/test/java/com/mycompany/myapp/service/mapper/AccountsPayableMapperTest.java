package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class AccountsPayableMapperTest {

    private AccountsPayableMapper accountsPayableMapper;

    @BeforeEach
    public void setUp() {
        accountsPayableMapper = new AccountsPayableMapperImpl();
    }
}
