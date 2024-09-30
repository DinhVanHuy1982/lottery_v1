package com.lottery.app.repository;

import com.lottery.app.domain.RoleFunctionAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RoleFunctionAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleFunctionActionRepository extends JpaRepository<RoleFunctionAction, Long> {}
