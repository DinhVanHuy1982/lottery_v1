package com.lottery.app.repository;

import com.lottery.app.domain.Prizes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prizes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrizesRepository extends JpaRepository<Prizes, Long> {}
