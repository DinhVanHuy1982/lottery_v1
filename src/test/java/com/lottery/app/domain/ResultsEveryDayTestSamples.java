package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ResultsEveryDayTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ResultsEveryDay getResultsEveryDaySample1() {
        return new ResultsEveryDay().id(1L).prizeCode("prizeCode1").result("result1");
    }

    public static ResultsEveryDay getResultsEveryDaySample2() {
        return new ResultsEveryDay().id(2L).prizeCode("prizeCode2").result("result2");
    }

    public static ResultsEveryDay getResultsEveryDayRandomSampleGenerator() {
        return new ResultsEveryDay()
            .id(longCount.incrementAndGet())
            .prizeCode(UUID.randomUUID().toString())
            .result(UUID.randomUUID().toString());
    }
}
