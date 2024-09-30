package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IntroduceArticleGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IntroduceArticleGroup getIntroduceArticleGroupSample1() {
        return new IntroduceArticleGroup()
            .id(1L)
            .code("code1")
            .articleGroupCode("articleGroupCode1")
            .fileId("fileId1")
            .titleIntroduce("titleIntroduce1")
            .contentIntroduce("contentIntroduce1");
    }

    public static IntroduceArticleGroup getIntroduceArticleGroupSample2() {
        return new IntroduceArticleGroup()
            .id(2L)
            .code("code2")
            .articleGroupCode("articleGroupCode2")
            .fileId("fileId2")
            .titleIntroduce("titleIntroduce2")
            .contentIntroduce("contentIntroduce2");
    }

    public static IntroduceArticleGroup getIntroduceArticleGroupRandomSampleGenerator() {
        return new IntroduceArticleGroup()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .articleGroupCode(UUID.randomUUID().toString())
            .fileId(UUID.randomUUID().toString())
            .titleIntroduce(UUID.randomUUID().toString())
            .contentIntroduce(UUID.randomUUID().toString());
    }
}
