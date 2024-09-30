package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RolesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Roles getRolesSample1() {
        return new Roles()
            .id(1L)
            .name("name1")
            .code("code1")
            .status(1L)
            .description("description1")
            .createName("createName1")
            .updateName("updateName1");
    }

    public static Roles getRolesSample2() {
        return new Roles()
            .id(2L)
            .name("name2")
            .code("code2")
            .status(2L)
            .description("description2")
            .createName("createName2")
            .updateName("updateName2");
    }

    public static Roles getRolesRandomSampleGenerator() {
        return new Roles()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .status(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .createName(UUID.randomUUID().toString())
            .updateName(UUID.randomUUID().toString());
    }
}
