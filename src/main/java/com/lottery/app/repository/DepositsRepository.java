package com.lottery.app.repository;

import com.lottery.app.domain.Deposits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deposits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositsRepository extends JpaRepository<Deposits, Long> {}
