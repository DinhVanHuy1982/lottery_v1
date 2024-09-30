package com.lottery.app.service.mapper;

import com.lottery.app.domain.RandomResults;
import com.lottery.app.service.dto.RandomResultsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RandomResults} and its DTO {@link RandomResultsDTO}.
 */
@Mapper(componentModel = "spring")
public interface RandomResultsMapper extends EntityMapper<RandomResultsDTO, RandomResults> {}
