package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContactInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContactInfo getContactInfoSample1() {
        return new ContactInfo().id(1L).positsion("positsion1").firstName("firstName1").lastName("lastName1");
    }

    public static ContactInfo getContactInfoSample2() {
        return new ContactInfo().id(2L).positsion("positsion2").firstName("firstName2").lastName("lastName2");
    }

    public static ContactInfo getContactInfoRandomSampleGenerator() {
        return new ContactInfo()
            .id(longCount.incrementAndGet())
            .positsion(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString());
    }
}
