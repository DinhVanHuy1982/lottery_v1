package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IntroduceArticleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IntroduceArticle getIntroduceArticleSample1() {
        return new IntroduceArticle()
            .id(1L)
            .code("code1")
            .articleCode("articleCode1")
            .title("title1")
            .content("content1")
            .fileId("fileId1");
    }

    public static IntroduceArticle getIntroduceArticleSample2() {
        return new IntroduceArticle()
            .id(2L)
            .code("code2")
            .articleCode("articleCode2")
            .title("title2")
            .content("content2")
            .fileId("fileId2");
    }

    public static IntroduceArticle getIntroduceArticleRandomSampleGenerator() {
        return new IntroduceArticle()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .articleCode(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .content(UUID.randomUUID().toString())
            .fileId(UUID.randomUUID().toString());
    }
}
