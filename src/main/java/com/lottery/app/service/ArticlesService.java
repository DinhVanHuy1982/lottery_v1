package com.lottery.app.service;

import com.lottery.app.config.ServiceResult;
import com.lottery.app.service.dto.ArticlesDTO;
import com.lottery.app.service.dto.SearchDTO;
import com.lottery.app.service.dto.body.search.ArticlesSearchDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.Articles}.
 */
public interface ArticlesService {
    /**
     * Save a articles.
     *
     * @param articlesDTO the entity to save.
     * @return the persisted entity.
     */
    ArticlesDTO save(ArticlesDTO articlesDTO);

    /**
     * Updates a articles.
     *
     * @param articlesDTO the entity to update.
     * @return the persisted entity.
     */
    ArticlesDTO update(ArticlesDTO articlesDTO);

    /**
     * Partially updates a articles.
     *
     * @param articlesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArticlesDTO> partialUpdate(ArticlesDTO articlesDTO);

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticlesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" articles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticlesDTO> findOne(Long id);

    /**
     * Delete the "id" articles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ServiceResult<String> createUpdateAriticle(ArticlesDTO articlesDTO);

    ServiceResult<Page<ArticlesDTO>> searchArticles(SearchDTO<ArticlesSearchDTO> searchDTO);
}
