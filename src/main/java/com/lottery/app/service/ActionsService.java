package com.lottery.app.service;

import com.lottery.app.service.dto.ActionsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.Actions}.
 */
public interface ActionsService {
    /**
     * Save a actions.
     *
     * @param actionsDTO the entity to save.
     * @return the persisted entity.
     */
    ActionsDTO save(ActionsDTO actionsDTO);

    /**
     * Updates a actions.
     *
     * @param actionsDTO the entity to update.
     * @return the persisted entity.
     */
    ActionsDTO update(ActionsDTO actionsDTO);

    /**
     * Partially updates a actions.
     *
     * @param actionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ActionsDTO> partialUpdate(ActionsDTO actionsDTO);

    /**
     * Get all the actions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" actions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActionsDTO> findOne(Long id);

    /**
     * Delete the "id" actions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
