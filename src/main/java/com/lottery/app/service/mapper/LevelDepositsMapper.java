package com.lottery.app.service.mapper;

import com.lottery.app.domain.LevelDeposits;
import com.lottery.app.service.dto.LevelDepositsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LevelDeposits} and its DTO {@link LevelDepositsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LevelDepositsMapper extends EntityMapper<LevelDepositsDTO, LevelDeposits> {}
