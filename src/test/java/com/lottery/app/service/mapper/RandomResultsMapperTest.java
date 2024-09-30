package com.lottery.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class RandomResultsMapperTest {

    private RandomResultsMapper randomResultsMapper;

    @BeforeEach
    public void setUp() {
        randomResultsMapper = new RandomResultsMapperImpl();
    }
}
