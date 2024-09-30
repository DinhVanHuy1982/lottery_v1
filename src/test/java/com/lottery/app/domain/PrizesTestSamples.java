package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PrizesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prizes getPrizesSample1() {
        return new Prizes()
            .id(1L)
            .code("code1")
            .articleCode("articleCode1")
            .levelCup("levelCup1")
            .numberPrize(1L)
            .createName("createName1")
            .updateName("updateName1");
    }

    public static Prizes getPrizesSample2() {
        return new Prizes()
            .id(2L)
            .code("code2")
            .articleCode("articleCode2")
            .levelCup("levelCup2")
            .numberPrize(2L)
            .createName("createName2")
            .updateName("updateName2");
    }

    public static Prizes getPrizesRandomSampleGenerator() {
        return new Prizes()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .articleCode(UUID.randomUUID().toString())
            .levelCup(UUID.randomUUID().toString())
            .numberPrize(longCount.incrementAndGet())
            .createName(UUID.randomUUID().toString())
            .updateName(UUID.randomUUID().toString());
    }
}
