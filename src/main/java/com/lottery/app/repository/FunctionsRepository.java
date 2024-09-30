package com.lottery.app.repository;

import com.lottery.app.domain.Functions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Functions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionsRepository extends JpaRepository<Functions, Long> {}
