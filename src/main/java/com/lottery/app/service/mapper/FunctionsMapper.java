package com.lottery.app.service.mapper;

import com.lottery.app.domain.Functions;
import com.lottery.app.service.dto.FunctionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Functions} and its DTO {@link FunctionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface FunctionsMapper extends EntityMapper<FunctionsDTO, Functions> {}
