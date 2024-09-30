package com.lottery.app.service;

import com.lottery.app.service.dto.DepositsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.Deposits}.
 */
public interface DepositsService {
    /**
     * Save a deposits.
     *
     * @param depositsDTO the entity to save.
     * @return the persisted entity.
     */
    DepositsDTO save(DepositsDTO depositsDTO);

    /**
     * Updates a deposits.
     *
     * @param depositsDTO the entity to update.
     * @return the persisted entity.
     */
    DepositsDTO update(DepositsDTO depositsDTO);

    /**
     * Partially updates a deposits.
     *
     * @param depositsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DepositsDTO> partialUpdate(DepositsDTO depositsDTO);

    /**
     * Get all the deposits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepositsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" deposits.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepositsDTO> findOne(Long id);

    /**
     * Delete the "id" deposits.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
