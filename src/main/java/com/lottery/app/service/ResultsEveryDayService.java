package com.lottery.app.service;

import com.lottery.app.service.dto.ResultsEveryDayDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.ResultsEveryDay}.
 */
public interface ResultsEveryDayService {
    /**
     * Save a resultsEveryDay.
     *
     * @param resultsEveryDayDTO the entity to save.
     * @return the persisted entity.
     */
    ResultsEveryDayDTO save(ResultsEveryDayDTO resultsEveryDayDTO);

    /**
     * Updates a resultsEveryDay.
     *
     * @param resultsEveryDayDTO the entity to update.
     * @return the persisted entity.
     */
    ResultsEveryDayDTO update(ResultsEveryDayDTO resultsEveryDayDTO);

    /**
     * Partially updates a resultsEveryDay.
     *
     * @param resultsEveryDayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResultsEveryDayDTO> partialUpdate(ResultsEveryDayDTO resultsEveryDayDTO);

    /**
     * Get all the resultsEveryDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResultsEveryDayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" resultsEveryDay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultsEveryDayDTO> findOne(Long id);

    /**
     * Delete the "id" resultsEveryDay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
