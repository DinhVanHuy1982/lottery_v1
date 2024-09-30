package com.lottery.app.repository;

import com.lottery.app.domain.Articles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Articles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {}
