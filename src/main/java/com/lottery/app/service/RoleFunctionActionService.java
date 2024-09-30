package com.lottery.app.service;

import com.lottery.app.service.dto.RoleFunctionActionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.RoleFunctionAction}.
 */
public interface RoleFunctionActionService {
    /**
     * Save a roleFunctionAction.
     *
     * @param roleFunctionActionDTO the entity to save.
     * @return the persisted entity.
     */
    RoleFunctionActionDTO save(RoleFunctionActionDTO roleFunctionActionDTO);

    /**
     * Updates a roleFunctionAction.
     *
     * @param roleFunctionActionDTO the entity to update.
     * @return the persisted entity.
     */
    RoleFunctionActionDTO update(RoleFunctionActionDTO roleFunctionActionDTO);

    /**
     * Partially updates a roleFunctionAction.
     *
     * @param roleFunctionActionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoleFunctionActionDTO> partialUpdate(RoleFunctionActionDTO roleFunctionActionDTO);

    /**
     * Get all the roleFunctionActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleFunctionActionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" roleFunctionAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleFunctionActionDTO> findOne(Long id);

    /**
     * Delete the "id" roleFunctionAction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
