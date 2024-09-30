package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RandomResultsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RandomResults getRandomResultsSample1() {
        return new RandomResults()
            .id(1L)
            .prizeCode("prizeCode1")
            .result("result1")
            .randomUserPlay(1L)
            .userPlay(1L)
            .randomUserSuccess(1L)
            .userSuccess(1L);
    }

    public static RandomResults getRandomResultsSample2() {
        return new RandomResults()
            .id(2L)
            .prizeCode("prizeCode2")
            .result("result2")
            .randomUserPlay(2L)
            .userPlay(2L)
            .randomUserSuccess(2L)
            .userSuccess(2L);
    }

    public static RandomResults getRandomResultsRandomSampleGenerator() {
        return new RandomResults()
            .id(longCount.incrementAndGet())
            .prizeCode(UUID.randomUUID().toString())
            .result(UUID.randomUUID().toString())
            .randomUserPlay(longCount.incrementAndGet())
            .userPlay(longCount.incrementAndGet())
            .randomUserSuccess(longCount.incrementAndGet())
            .userSuccess(longCount.incrementAndGet());
    }
}
