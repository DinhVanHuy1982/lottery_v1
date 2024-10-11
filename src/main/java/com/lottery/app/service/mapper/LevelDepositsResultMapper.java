package com.lottery.app.service.mapper;

import com.lottery.app.domain.LevelDepositsResult;
import com.lottery.app.service.dto.LevelDepositsResultDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LevelDepositsResult} and its DTO {@link LevelDepositsResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface LevelDepositsResultMapper extends EntityMapper<LevelDepositsResultDTO, LevelDepositsResult> {}
