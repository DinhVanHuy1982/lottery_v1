package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RoleFunctionActionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RoleFunctionAction getRoleFunctionActionSample1() {
        return new RoleFunctionAction().id(1L).roleFunctionCode("roleFunctionCode1").actionCode("actionCode1");
    }

    public static RoleFunctionAction getRoleFunctionActionSample2() {
        return new RoleFunctionAction().id(2L).roleFunctionCode("roleFunctionCode2").actionCode("actionCode2");
    }

    public static RoleFunctionAction getRoleFunctionActionRandomSampleGenerator() {
        return new RoleFunctionAction()
            .id(longCount.incrementAndGet())
            .roleFunctionCode(UUID.randomUUID().toString())
            .actionCode(UUID.randomUUID().toString());
    }
}
