package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CIPhoneNumTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CIPhoneNum getCIPhoneNumSample1() {
        return new CIPhoneNum().id(1L).phoneNum("phoneNum1");
    }

    public static CIPhoneNum getCIPhoneNumSample2() {
        return new CIPhoneNum().id(2L).phoneNum("phoneNum2");
    }

    public static CIPhoneNum getCIPhoneNumRandomSampleGenerator() {
        return new CIPhoneNum().id(longCount.incrementAndGet()).phoneNum(UUID.randomUUID().toString());
    }
}
