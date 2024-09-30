package com.lottery.app.service;

import com.lottery.app.service.dto.IntroduceArticleGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.IntroduceArticleGroup}.
 */
public interface IntroduceArticleGroupService {
    /**
     * Save a introduceArticleGroup.
     *
     * @param introduceArticleGroupDTO the entity to save.
     * @return the persisted entity.
     */
    IntroduceArticleGroupDTO save(IntroduceArticleGroupDTO introduceArticleGroupDTO);

    /**
     * Updates a introduceArticleGroup.
     *
     * @param introduceArticleGroupDTO the entity to update.
     * @return the persisted entity.
     */
    IntroduceArticleGroupDTO update(IntroduceArticleGroupDTO introduceArticleGroupDTO);

    /**
     * Partially updates a introduceArticleGroup.
     *
     * @param introduceArticleGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntroduceArticleGroupDTO> partialUpdate(IntroduceArticleGroupDTO introduceArticleGroupDTO);

    /**
     * Get all the introduceArticleGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntroduceArticleGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" introduceArticleGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntroduceArticleGroupDTO> findOne(Long id);

    /**
     * Delete the "id" introduceArticleGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
