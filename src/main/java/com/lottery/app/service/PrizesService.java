package com.lottery.app.service;

import com.lottery.app.service.dto.PrizesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.Prizes}.
 */
public interface PrizesService {
    /**
     * Save a prizes.
     *
     * @param prizesDTO the entity to save.
     * @return the persisted entity.
     */
    PrizesDTO save(PrizesDTO prizesDTO);

    /**
     * Updates a prizes.
     *
     * @param prizesDTO the entity to update.
     * @return the persisted entity.
     */
    PrizesDTO update(PrizesDTO prizesDTO);

    /**
     * Partially updates a prizes.
     *
     * @param prizesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrizesDTO> partialUpdate(PrizesDTO prizesDTO);

    /**
     * Get all the prizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrizesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" prizes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrizesDTO> findOne(Long id);

    /**
     * Delete the "id" prizes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
