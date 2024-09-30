package com.lottery.app.service.mapper;

import com.lottery.app.domain.Deposits;
import com.lottery.app.service.dto.DepositsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deposits} and its DTO {@link DepositsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepositsMapper extends EntityMapper<DepositsDTO, Deposits> {}
