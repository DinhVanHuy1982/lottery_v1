package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RoleFunctionsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RoleFunctions getRoleFunctionsSample1() {
        return new RoleFunctions().id(1L).code("code1").roleCode("roleCode1").functionCode("functionCode1");
    }

    public static RoleFunctions getRoleFunctionsSample2() {
        return new RoleFunctions().id(2L).code("code2").roleCode("roleCode2").functionCode("functionCode2");
    }

    public static RoleFunctions getRoleFunctionsRandomSampleGenerator() {
        return new RoleFunctions()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .roleCode(UUID.randomUUID().toString())
            .functionCode(UUID.randomUUID().toString());
    }
}
