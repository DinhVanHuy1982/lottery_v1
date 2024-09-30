package com.lottery.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FileSavesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FileSaves getFileSavesSample1() {
        return new FileSaves().id(1L).fileId("fileId1").fileName("fileName1").filePath("filePath1");
    }

    public static FileSaves getFileSavesSample2() {
        return new FileSaves().id(2L).fileId("fileId2").fileName("fileName2").filePath("filePath2");
    }

    public static FileSaves getFileSavesRandomSampleGenerator() {
        return new FileSaves()
            .id(longCount.incrementAndGet())
            .fileId(UUID.randomUUID().toString())
            .fileName(UUID.randomUUID().toString())
            .filePath(UUID.randomUUID().toString());
    }
}
