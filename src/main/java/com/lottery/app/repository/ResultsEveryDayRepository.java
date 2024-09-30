package com.lottery.app.repository;

import com.lottery.app.domain.ResultsEveryDay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResultsEveryDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultsEveryDayRepository extends JpaRepository<ResultsEveryDay, Long> {}
