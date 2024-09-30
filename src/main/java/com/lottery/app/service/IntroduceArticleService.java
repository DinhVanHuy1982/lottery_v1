package com.lottery.app.service;

import com.lottery.app.service.dto.IntroduceArticleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.IntroduceArticle}.
 */
public interface IntroduceArticleService {
    /**
     * Save a introduceArticle.
     *
     * @param introduceArticleDTO the entity to save.
     * @return the persisted entity.
     */
    IntroduceArticleDTO save(IntroduceArticleDTO introduceArticleDTO);

    /**
     * Updates a introduceArticle.
     *
     * @param introduceArticleDTO the entity to update.
     * @return the persisted entity.
     */
    IntroduceArticleDTO update(IntroduceArticleDTO introduceArticleDTO);

    /**
     * Partially updates a introduceArticle.
     *
     * @param introduceArticleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntroduceArticleDTO> partialUpdate(IntroduceArticleDTO introduceArticleDTO);

    /**
     * Get all the introduceArticles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntroduceArticleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" introduceArticle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntroduceArticleDTO> findOne(Long id);

    /**
     * Delete the "id" introduceArticle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
