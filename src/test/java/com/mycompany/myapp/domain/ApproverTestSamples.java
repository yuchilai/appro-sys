package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ApproverTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Approver getApproverSample1() {
        return new Approver().id(1L);
    }

    public static Approver getApproverSample2() {
        return new Approver().id(2L);
    }

    public static Approver getApproverRandomSampleGenerator() {
        return new Approver().id(longCount.incrementAndGet());
    }
}
