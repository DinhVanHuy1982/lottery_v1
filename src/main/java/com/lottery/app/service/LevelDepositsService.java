package com.lottery.app.service;

import com.lottery.app.service.dto.LevelDepositsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.LevelDeposits}.
 */
public interface LevelDepositsService {
    /**
     * Save a levelDeposits.
     *
     * @param levelDepositsDTO the entity to save.
     * @return the persisted entity.
     */
    LevelDepositsDTO save(LevelDepositsDTO levelDepositsDTO);

    /**
     * Updates a levelDeposits.
     *
     * @param levelDepositsDTO the entity to update.
     * @return the persisted entity.
     */
    LevelDepositsDTO update(LevelDepositsDTO levelDepositsDTO);

    /**
     * Partially updates a levelDeposits.
     *
     * @param levelDepositsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LevelDepositsDTO> partialUpdate(LevelDepositsDTO levelDepositsDTO);

    /**
     * Get all the levelDeposits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LevelDepositsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" levelDeposits.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelDepositsDTO> findOne(Long id);

    /**
     * Delete the "id" levelDeposits.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
