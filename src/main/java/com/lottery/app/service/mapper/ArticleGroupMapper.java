package com.lottery.app.service.mapper;

import com.lottery.app.domain.ArticleGroup;
import com.lottery.app.service.dto.ArticleGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleGroup} and its DTO {@link ArticleGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleGroupMapper extends EntityMapper<ArticleGroupDTO, ArticleGroup> {}
