package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceBillingInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static InvoiceBillingInfo getInvoiceBillingInfoSample1() {
        return new InvoiceBillingInfo()
            .id(1L)
            .firstName("firstName1")
            .lastname("lastname1")
            .email("email1")
            .phoneNum("phoneNum1")
            .address1("address11")
            .address2("address21")
            .city("city1")
            .state("state1")
            .county("county1")
            .zipCode("zipCode1")
            .country("country1");
    }

    public static InvoiceBillingInfo getInvoiceBillingInfoSample2() {
        return new InvoiceBillingInfo()
            .id(2L)
            .firstName("firstName2")
            .lastname("lastname2")
            .email("email2")
            .phoneNum("phoneNum2")
            .address1("address12")
            .address2("address22")
            .city("city2")
            .state("state2")
            .county("county2")
            .zipCode("zipCode2")
            .country("country2");
    }

    public static InvoiceBillingInfo getInvoiceBillingInfoRandomSampleGenerator() {
        return new InvoiceBillingInfo()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastname(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phoneNum(UUID.randomUUID().toString())
            .address1(UUID.randomUUID().toString())
            .address2(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .county(UUID.randomUUID().toString())
            .zipCode(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString());
    }
}
