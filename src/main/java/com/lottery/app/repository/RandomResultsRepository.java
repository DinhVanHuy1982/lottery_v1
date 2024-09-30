package com.lottery.app.repository;

import com.lottery.app.domain.RandomResults;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RandomResults entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RandomResultsRepository extends JpaRepository<RandomResults, Long> {}
