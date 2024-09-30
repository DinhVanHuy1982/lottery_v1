package com.lottery.app.service;

import com.lottery.app.service.dto.ArticleGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.ArticleGroup}.
 */
public interface ArticleGroupService {
    /**
     * Save a articleGroup.
     *
     * @param articleGroupDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleGroupDTO save(ArticleGroupDTO articleGroupDTO);

    /**
     * Updates a articleGroup.
     *
     * @param articleGroupDTO the entity to update.
     * @return the persisted entity.
     */
    ArticleGroupDTO update(ArticleGroupDTO articleGroupDTO);

    /**
     * Partially updates a articleGroup.
     *
     * @param articleGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArticleGroupDTO> partialUpdate(ArticleGroupDTO articleGroupDTO);

    /**
     * Get all the articleGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" articleGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleGroupDTO> findOne(Long id);

    /**
     * Delete the "id" articleGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
