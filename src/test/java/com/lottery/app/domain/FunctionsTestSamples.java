package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FunctionsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Functions getFunctionsSample1() {
        return new Functions().id(1L).name("name1").code("code1");
    }

    public static Functions getFunctionsSample2() {
        return new Functions().id(2L).name("name2").code("code2");
    }

    public static Functions getFunctionsRandomSampleGenerator() {
        return new Functions().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).code(UUID.randomUUID().toString());
    }
}
