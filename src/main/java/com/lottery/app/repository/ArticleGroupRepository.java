package com.lottery.app.repository;

import com.lottery.app.domain.ArticleGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleGroupRepository extends JpaRepository<ArticleGroup, Long> {}
