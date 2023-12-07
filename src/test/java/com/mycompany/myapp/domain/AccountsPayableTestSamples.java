package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AccountsPayableTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AccountsPayable getAccountsPayableSample1() {
        return new AccountsPayable()
            .id(1L)
            .deptName("deptName1")
            .repLastName("repLastName1")
            .repFirstName("repFirstName1")
            .repEmail("repEmail1")
            .repPhoneNum("repPhoneNum1");
    }

    public static AccountsPayable getAccountsPayableSample2() {
        return new AccountsPayable()
            .id(2L)
            .deptName("deptName2")
            .repLastName("repLastName2")
            .repFirstName("repFirstName2")
            .repEmail("repEmail2")
            .repPhoneNum("repPhoneNum2");
    }

    public static AccountsPayable getAccountsPayableRandomSampleGenerator() {
        return new AccountsPayable()
            .id(longCount.incrementAndGet())
            .deptName(UUID.randomUUID().toString())
            .repLastName(UUID.randomUUID().toString())
            .repFirstName(UUID.randomUUID().toString())
            .repEmail(UUID.randomUUID().toString())
            .repPhoneNum(UUID.randomUUID().toString());
    }
}
