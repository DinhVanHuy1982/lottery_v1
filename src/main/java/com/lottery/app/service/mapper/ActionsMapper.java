package com.lottery.app.service.mapper;

import com.lottery.app.domain.Actions;
import com.lottery.app.service.dto.ActionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Actions} and its DTO {@link ActionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActionsMapper extends EntityMapper<ActionsDTO, Actions> {}
