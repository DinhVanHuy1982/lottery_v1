package com.lottery.app.service.mapper;

import com.lottery.app.domain.ResultsEveryDay;
import com.lottery.app.service.dto.ResultsEveryDayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResultsEveryDay} and its DTO {@link ResultsEveryDayDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResultsEveryDayMapper extends EntityMapper<ResultsEveryDayDTO, ResultsEveryDay> {}
