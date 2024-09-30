package com.lottery.app.service.mapper;

import com.lottery.app.domain.IntroduceArticle;
import com.lottery.app.service.dto.IntroduceArticleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntroduceArticle} and its DTO {@link IntroduceArticleDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntroduceArticleMapper extends EntityMapper<IntroduceArticleDTO, IntroduceArticle> {}
