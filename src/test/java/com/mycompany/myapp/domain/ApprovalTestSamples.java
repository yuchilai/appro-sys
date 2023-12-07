package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ApprovalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Approval getApprovalSample1() {
        return new Approval().id(1L);
    }

    public static Approval getApprovalSample2() {
        return new Approval().id(2L);
    }

    public static Approval getApprovalRandomSampleGenerator() {
        return new Approval().id(longCount.incrementAndGet());
    }
}
