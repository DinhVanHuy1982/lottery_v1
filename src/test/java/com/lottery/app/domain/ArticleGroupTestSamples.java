package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArticleGroup getArticleGroupSample1() {
        return new ArticleGroup()
            .id(1L)
            .code("code1")
            .title("title1")
            .mainContent("mainContent1")
            .createName("createName1")
            .updateName("updateName1")
            .fileName("fileName1")
            .filePath("filePath1")
            .fileId("fileId1");
    }

    public static ArticleGroup getArticleGroupSample2() {
        return new ArticleGroup()
            .id(2L)
            .code("code2")
            .title("title2")
            .mainContent("mainContent2")
            .createName("createName2")
            .updateName("updateName2")
            .fileName("fileName2")
            .filePath("filePath2")
            .fileId("fileId2");
    }

    public static ArticleGroup getArticleGroupRandomSampleGenerator() {
        return new ArticleGroup()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .mainContent(UUID.randomUUID().toString())
            .createName(UUID.randomUUID().toString())
            .updateName(UUID.randomUUID().toString())
            .fileName(UUID.randomUUID().toString())
            .filePath(UUID.randomUUID().toString())
            .fileId(UUID.randomUUID().toString());
    }
}
