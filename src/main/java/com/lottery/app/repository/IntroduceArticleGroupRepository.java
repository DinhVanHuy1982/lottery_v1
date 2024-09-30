package com.lottery.app.repository;

import com.lottery.app.domain.IntroduceArticleGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntroduceArticleGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntroduceArticleGroupRepository extends JpaRepository<IntroduceArticleGroup, Long> {}
