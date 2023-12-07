package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CCEmailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CCEmail getCCEmailSample1() {
        return new CCEmail().id(1L).email("email1");
    }

    public static CCEmail getCCEmailSample2() {
        return new CCEmail().id(2L).email("email2");
    }

    public static CCEmail getCCEmailRandomSampleGenerator() {
        return new CCEmail().id(longCount.incrementAndGet()).email(UUID.randomUUID().toString());
    }
}
