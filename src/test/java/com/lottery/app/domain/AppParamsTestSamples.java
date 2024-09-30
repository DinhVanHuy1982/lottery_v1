package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppParamsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppParams getAppParamsSample1() {
        return new AppParams().id(1L).code("code1").value("value1").type("type1");
    }

    public static AppParams getAppParamsSample2() {
        return new AppParams().id(2L).code("code2").value("value2").type("type2");
    }

    public static AppParams getAppParamsRandomSampleGenerator() {
        return new AppParams()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .value(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}
