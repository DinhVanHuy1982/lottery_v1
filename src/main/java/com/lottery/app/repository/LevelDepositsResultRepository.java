package com.lottery.app.repository;

import com.lottery.app.domain.LevelDepositsResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LevelDepositsResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelDepositsResultRepository extends JpaRepository<LevelDepositsResult, Long> {}
