package com.lottery.app.service.mapper;

import com.lottery.app.domain.Prizes;
import com.lottery.app.service.dto.PrizesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prizes} and its DTO {@link PrizesDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrizesMapper extends EntityMapper<PrizesDTO, Prizes> {}
