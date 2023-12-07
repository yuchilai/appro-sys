package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WorkEntryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static WorkEntry getWorkEntrySample1() {
        return new WorkEntry().id(1L).title("title1").hours(1).fileName("fileName1").fileType("fileType1").fileSize(1L);
    }

    public static WorkEntry getWorkEntrySample2() {
        return new WorkEntry().id(2L).title("title2").hours(2).fileName("fileName2").fileType("fileType2").fileSize(2L);
    }

    public static WorkEntry getWorkEntryRandomSampleGenerator() {
        return new WorkEntry()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .hours(intCount.incrementAndGet())
            .fileName(UUID.randomUUID().toString())
            .fileType(UUID.randomUUID().toString())
            .fileSize(longCount.incrementAndGet());
    }
}
