package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HourlyRateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HourlyRate getHourlyRateSample1() {
        return new HourlyRate().id(1L).name("name1");
    }

    public static HourlyRate getHourlyRateSample2() {
        return new HourlyRate().id(2L).name("name2");
    }

    public static HourlyRate getHourlyRateRandomSampleGenerator() {
        return new HourlyRate().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
