package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LevelDepositsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LevelDeposits getLevelDepositsSample1() {
        return new LevelDeposits().id(1L).code("code1").minPrice(1L).updateName("updateName1").articleCode("articleCode1");
    }

    public static LevelDeposits getLevelDepositsSample2() {
        return new LevelDeposits().id(2L).code("code2").minPrice(2L).updateName("updateName2").articleCode("articleCode2");
    }

    public static LevelDeposits getLevelDepositsRandomSampleGenerator() {
        return new LevelDeposits()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .minPrice(longCount.incrementAndGet())
            .updateName(UUID.randomUUID().toString())
            .articleCode(UUID.randomUUID().toString());
    }
}
