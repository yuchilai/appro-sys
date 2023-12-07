package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClientCompanyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ClientCompany getClientCompanySample1() {
        return new ClientCompany().id(1L).name("name1").invoicePrefix("invoicePrefix1");
    }

    public static ClientCompany getClientCompanySample2() {
        return new ClientCompany().id(2L).name("name2").invoicePrefix("invoicePrefix2");
    }

    public static ClientCompany getClientCompanyRandomSampleGenerator() {
        return new ClientCompany()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .invoicePrefix(UUID.randomUUID().toString());
    }
}
