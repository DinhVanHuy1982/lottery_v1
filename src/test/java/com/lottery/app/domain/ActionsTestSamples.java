package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActionsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Actions getActionsSample1() {
        return new Actions().id(1L).name("name1").code("code1");
    }

    public static Actions getActionsSample2() {
        return new Actions().id(2L).name("name2").code("code2");
    }

    public static Actions getActionsRandomSampleGenerator() {
        return new Actions().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).code(UUID.randomUUID().toString());
    }
}
