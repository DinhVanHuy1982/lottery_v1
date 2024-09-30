package com.lottery.app.service.mapper;

import com.lottery.app.domain.Articles;
import com.lottery.app.service.dto.ArticlesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Articles} and its DTO {@link ArticlesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticlesMapper extends EntityMapper<ArticlesDTO, Articles> {}
