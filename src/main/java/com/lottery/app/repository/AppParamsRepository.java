package com.lottery.app.repository;

import com.lottery.app.domain.AppParams;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppParams entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppParamsRepository extends JpaRepository<AppParams, Long> {}
