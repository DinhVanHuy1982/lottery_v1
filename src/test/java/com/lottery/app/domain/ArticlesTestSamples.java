package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticlesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Articles getArticlesSample1() {
        return new Articles()
            .id(1L)
            .code("code1")
            .title("title1")
            .content("content1")
            .fileId("fileId1")
            .updateName("updateName1")
            .numberChoice(1L)
            .numberOfDigits(1L)
            .timeStart("timeStart1")
            .timeEnd("timeEnd1");
    }

    public static Articles getArticlesSample2() {
        return new Articles()
            .id(2L)
            .code("code2")
            .title("title2")
            .content("content2")
            .fileId("fileId2")
            .updateName("updateName2")
            .numberChoice(2L)
            .numberOfDigits(2L)
            .timeStart("timeStart2")
            .timeEnd("timeEnd2");
    }

    public static Articles getArticlesRandomSampleGenerator() {
        return new Articles()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .content(UUID.randomUUID().toString())
            .fileId(UUID.randomUUID().toString())
            .updateName(UUID.randomUUID().toString())
            .numberChoice(longCount.incrementAndGet())
            .numberOfDigits(longCount.incrementAndGet())
            .timeStart(UUID.randomUUID().toString())
            .timeEnd(UUID.randomUUID().toString());
    }
}
