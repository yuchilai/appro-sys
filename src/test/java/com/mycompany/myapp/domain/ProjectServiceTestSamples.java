package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectServiceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProjectService getProjectServiceSample1() {
        return new ProjectService().id(1L).name("name1").dayLength(1);
    }

    public static ProjectService getProjectServiceSample2() {
        return new ProjectService().id(2L).name("name2").dayLength(2);
    }

    public static ProjectService getProjectServiceRandomSampleGenerator() {
        return new ProjectService()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .dayLength(intCount.incrementAndGet());
    }
}
