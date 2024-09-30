package com.lottery.app.repository;

import com.lottery.app.domain.FileSaves;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FileSaves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileSavesRepository extends JpaRepository<FileSaves, Long> {}
