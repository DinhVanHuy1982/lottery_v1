package com.lottery.app.service;

import com.lottery.app.service.dto.RandomResultsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.RandomResults}.
 */
public interface RandomResultsService {
    /**
     * Save a randomResults.
     *
     * @param randomResultsDTO the entity to save.
     * @return the persisted entity.
     */
    RandomResultsDTO save(RandomResultsDTO randomResultsDTO);

    /**
     * Updates a randomResults.
     *
     * @param randomResultsDTO the entity to update.
     * @return the persisted entity.
     */
    RandomResultsDTO update(RandomResultsDTO randomResultsDTO);

    /**
     * Partially updates a randomResults.
     *
     * @param randomResultsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RandomResultsDTO> partialUpdate(RandomResultsDTO randomResultsDTO);

    /**
     * Get all the randomResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RandomResultsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" randomResults.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RandomResultsDTO> findOne(Long id);

    /**
     * Delete the "id" randomResults.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
