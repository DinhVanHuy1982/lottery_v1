package com.lottery.app.service;

import com.lottery.app.service.dto.AppParamsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.AppParams}.
 */
public interface AppParamsService {
    /**
     * Save a appParams.
     *
     * @param appParamsDTO the entity to save.
     * @return the persisted entity.
     */
    AppParamsDTO save(AppParamsDTO appParamsDTO);

    /**
     * Updates a appParams.
     *
     * @param appParamsDTO the entity to update.
     * @return the persisted entity.
     */
    AppParamsDTO update(AppParamsDTO appParamsDTO);

    /**
     * Partially updates a appParams.
     *
     * @param appParamsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppParamsDTO> partialUpdate(AppParamsDTO appParamsDTO);

    /**
     * Get all the appParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppParamsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" appParams.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppParamsDTO> findOne(Long id);

    /**
     * Delete the "id" appParams.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
