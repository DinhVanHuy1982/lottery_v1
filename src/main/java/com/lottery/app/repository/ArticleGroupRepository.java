package com.lottery.app.repository;

import com.lottery.app.domain.ArticleGroup;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleGroupRepository extends JpaRepository<ArticleGroup, Long> {
    @Query(value = "SELECT ag.code as code, ag.name as name FROM article_group ag", nativeQuery = true)
    List<Map<String, Object>> getLstArticleGroupCodeAndName();
}
