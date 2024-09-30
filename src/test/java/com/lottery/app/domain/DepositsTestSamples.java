package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DepositsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Deposits getDepositsSample1() {
        return new Deposits()
            .id(1L)
            .articleCode("articleCode1")
            .netwrokCard("netwrokCard1")
            .valueCard("valueCard1")
            .seriCard("seriCard1")
            .codeCard("codeCard1")
            .status(1L)
            .userAppose("userAppose1")
            .valueChoice("valueChoice1")
            .phoneNumber("phoneNumber1");
    }

    public static Deposits getDepositsSample2() {
        return new Deposits()
            .id(2L)
            .articleCode("articleCode2")
            .netwrokCard("netwrokCard2")
            .valueCard("valueCard2")
            .seriCard("seriCard2")
            .codeCard("codeCard2")
            .status(2L)
            .userAppose("userAppose2")
            .valueChoice("valueChoice2")
            .phoneNumber("phoneNumber2");
    }

    public static Deposits getDepositsRandomSampleGenerator() {
        return new Deposits()
            .id(longCount.incrementAndGet())
            .articleCode(UUID.randomUUID().toString())
            .netwrokCard(UUID.randomUUID().toString())
            .valueCard(UUID.randomUUID().toString())
            .seriCard(UUID.randomUUID().toString())
            .codeCard(UUID.randomUUID().toString())
            .status(longCount.incrementAndGet())
            .userAppose(UUID.randomUUID().toString())
            .valueChoice(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString());
    }
}
