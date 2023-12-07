package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CCPhoneNumTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CCPhoneNum getCCPhoneNumSample1() {
        return new CCPhoneNum().id(1L).phoneNum("phoneNum1");
    }

    public static CCPhoneNum getCCPhoneNumSample2() {
        return new CCPhoneNum().id(2L).phoneNum("phoneNum2");
    }

    public static CCPhoneNum getCCPhoneNumRandomSampleGenerator() {
        return new CCPhoneNum().id(longCount.incrementAndGet()).phoneNum(UUID.randomUUID().toString());
    }
}
