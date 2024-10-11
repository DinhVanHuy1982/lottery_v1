package com.lottery.app.service;

import com.lottery.app.service.dto.LevelDepositsResultDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.LevelDepositsResult}.
 */
public interface LevelDepositsResultService {
    /**
     * Save a levelDepositsResult.
     *
     * @param levelDepositsResultDTO the entity to save.
     * @return the persisted entity.
     */
    LevelDepositsResultDTO save(LevelDepositsResultDTO levelDepositsResultDTO);

    /**
     * Updates a levelDepositsResult.
     *
     * @param levelDepositsResultDTO the entity to update.
     * @return the persisted entity.
     */
    LevelDepositsResultDTO update(LevelDepositsResultDTO levelDepositsResultDTO);

    /**
     * Partially updates a levelDepositsResult.
     *
     * @param levelDepositsResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelDepositsResultDTO> partialUpdate(LevelDepositsResultDTO levelDepositsResultDTO);

    /**
     * Get all the levelDepositsResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelDepositsResultDTO> findAll(Pageable pageable);

    /**
     * Get the "id" levelDepositsResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelDepositsResultDTO> findOne(Long id);

    /**
     * Delete the "id" levelDepositsResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
