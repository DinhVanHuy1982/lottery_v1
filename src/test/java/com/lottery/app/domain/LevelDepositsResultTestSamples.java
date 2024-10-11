package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LevelDepositsResultTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LevelDepositsResult getLevelDepositsResultSample1() {
        return new LevelDepositsResult().id(1L).code("code1").levelDepositsCode("levelDepositsCode1").randomResultCode("randomResultCode1");
    }

    public static LevelDepositsResult getLevelDepositsResultSample2() {
        return new LevelDepositsResult().id(2L).code("code2").levelDepositsCode("levelDepositsCode2").randomResultCode("randomResultCode2");
    }

    public static LevelDepositsResult getLevelDepositsResultRandomSampleGenerator() {
        return new LevelDepositsResult()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .levelDepositsCode(UUID.randomUUID().toString())
            .randomResultCode(UUID.randomUUID().toString());
    }
}
