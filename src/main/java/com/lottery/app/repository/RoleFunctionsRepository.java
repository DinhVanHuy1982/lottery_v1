package com.lottery.app.repository;

import com.lottery.app.domain.RoleFunctions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RoleFunctions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleFunctionsRepository extends JpaRepository<RoleFunctions, Long> {}
