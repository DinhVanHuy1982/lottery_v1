package com.lottery.app.service.mapper;

import com.lottery.app.domain.RoleFunctions;
import com.lottery.app.service.dto.RoleFunctionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleFunctions} and its DTO {@link RoleFunctionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoleFunctionsMapper extends EntityMapper<RoleFunctionsDTO, RoleFunctions> {}
