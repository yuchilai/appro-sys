package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class InvoiceBillingInfoMapperTest {

    private InvoiceBillingInfoMapper invoiceBillingInfoMapper;

    @BeforeEach
    public void setUp() {
        invoiceBillingInfoMapper = new InvoiceBillingInfoMapperImpl();
    }
}
