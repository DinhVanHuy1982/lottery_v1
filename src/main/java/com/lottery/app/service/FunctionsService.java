package com.lottery.app.service;

import com.lottery.app.service.dto.FunctionsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.Functions}.
 */
public interface FunctionsService {
    /**
     * Save a functions.
     *
     * @param functionsDTO the entity to save.
     * @return the persisted entity.
     */
    FunctionsDTO save(FunctionsDTO functionsDTO);

    /**
     * Updates a functions.
     *
     * @param functionsDTO the entity to update.
     * @return the persisted entity.
     */
    FunctionsDTO update(FunctionsDTO functionsDTO);

    /**
     * Partially updates a functions.
     *
     * @param functionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FunctionsDTO> partialUpdate(FunctionsDTO functionsDTO);

    /**
     * Get all the functions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FunctionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" functions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FunctionsDTO> findOne(Long id);

    /**
     * Delete the "id" functions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
