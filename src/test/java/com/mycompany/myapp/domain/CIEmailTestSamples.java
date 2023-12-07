package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CIEmailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CIEmail getCIEmailSample1() {
        return new CIEmail().id(1L).email("email1");
    }

    public static CIEmail getCIEmailSample2() {
        return new CIEmail().id(2L).email("email2");
    }

    public static CIEmail getCIEmailRandomSampleGenerator() {
        return new CIEmail().id(longCount.incrementAndGet()).email(UUID.randomUUID().toString());
    }
}
