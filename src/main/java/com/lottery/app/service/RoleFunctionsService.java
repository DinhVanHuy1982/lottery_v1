package com.lottery.app.service;

import com.lottery.app.service.dto.RoleFunctionsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.RoleFunctions}.
 */
public interface RoleFunctionsService {
    /**
     * Save a roleFunctions.
     *
     * @param roleFunctionsDTO the entity to save.
     * @return the persisted entity.
     */
    RoleFunctionsDTO save(RoleFunctionsDTO roleFunctionsDTO);

    /**
     * Updates a roleFunctions.
     *
     * @param roleFunctionsDTO the entity to update.
     * @return the persisted entity.
     */
    RoleFunctionsDTO update(RoleFunctionsDTO roleFunctionsDTO);

    /**
     * Partially updates a roleFunctions.
     *
     * @param roleFunctionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoleFunctionsDTO> partialUpdate(RoleFunctionsDTO roleFunctionsDTO);

    /**
     * Get all the roleFunctions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleFunctionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" roleFunctions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleFunctionsDTO> findOne(Long id);

    /**
     * Delete the "id" roleFunctions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
