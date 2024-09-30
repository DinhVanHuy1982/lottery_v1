package com.lottery.app.service.mapper;

import com.lottery.app.domain.Roles;
import com.lottery.app.service.dto.RolesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Roles} and its DTO {@link RolesDTO}.
 */
@Mapper(componentModel = "spring")
public interface RolesMapper extends EntityMapper<RolesDTO, Roles> {}
