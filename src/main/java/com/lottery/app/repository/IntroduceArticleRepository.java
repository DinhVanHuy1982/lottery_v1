package com.lottery.app.repository;

import com.lottery.app.domain.IntroduceArticle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntroduceArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntroduceArticleRepository extends JpaRepository<IntroduceArticle, Long> {}
