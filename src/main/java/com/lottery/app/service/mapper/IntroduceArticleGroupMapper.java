package com.lottery.app.service.mapper;

import com.lottery.app.domain.IntroduceArticleGroup;
import com.lottery.app.service.dto.IntroduceArticleGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntroduceArticleGroup} and its DTO {@link IntroduceArticleGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntroduceArticleGroupMapper extends EntityMapper<IntroduceArticleGroupDTO, IntroduceArticleGroup> {}
