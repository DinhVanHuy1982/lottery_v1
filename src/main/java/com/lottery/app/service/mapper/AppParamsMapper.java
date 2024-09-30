package com.lottery.app.service.mapper;

import com.lottery.app.domain.AppParams;
import com.lottery.app.service.dto.AppParamsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppParams} and its DTO {@link AppParamsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppParamsMapper extends EntityMapper<AppParamsDTO, AppParams> {}
