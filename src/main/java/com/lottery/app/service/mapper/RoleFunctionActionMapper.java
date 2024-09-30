package com.lottery.app.service.mapper;

import com.lottery.app.domain.RoleFunctionAction;
import com.lottery.app.service.dto.RoleFunctionActionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleFunctionAction} and its DTO {@link RoleFunctionActionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoleFunctionActionMapper extends EntityMapper<RoleFunctionActionDTO, RoleFunctionAction> {}
