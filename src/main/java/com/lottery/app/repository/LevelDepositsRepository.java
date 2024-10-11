package com.lottery.app.repository;

import com.lottery.app.domain.LevelDeposits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LevelDeposits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelDepositsRepository extends JpaRepository<LevelDeposits, Long> {}
