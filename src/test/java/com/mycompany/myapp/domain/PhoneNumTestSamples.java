package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PhoneNumTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PhoneNum getPhoneNumSample1() {
        return new PhoneNum().id(1L).phoneNum("phoneNum1");
    }

    public static PhoneNum getPhoneNumSample2() {
        return new PhoneNum().id(2L).phoneNum("phoneNum2");
    }

    public static PhoneNum getPhoneNumRandomSampleGenerator() {
        return new PhoneNum().id(longCount.incrementAndGet()).phoneNum(UUID.randomUUID().toString());
    }
}
