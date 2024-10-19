package com.lottery.app.web.rest;

import com.lottery.app.config.ServiceResult;
import com.lottery.app.repository.ArticleGroupRepository;
import com.lottery.app.service.ArticleGroupService;
import com.lottery.app.service.dto.ArticleGroupDTO;
import com.lottery.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lottery.app.domain.ArticleGroup}.
 */
@RestController
@RequestMapping("/api")
public class ArticleGroupResource {

    private final Logger log = LoggerFactory.getLogger(ArticleGroupResource.class);

    private static final String ENTITY_NAME = "articleGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleGroupService articleGroupService;

    private final ArticleGroupRepository articleGroupRepository;

    public ArticleGroupResource(ArticleGroupService articleGroupService, ArticleGroupRepository articleGroupRepository) {
        this.articleGroupService = articleGroupService;
        this.articleGroupRepository = articleGroupRepository;
    }

    /**
     * {@code GET  /article-groups} : get all the articleGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleGroups in body.
     */
    @GetMapping("/QLNVB")
    public ResponseEntity<List<ArticleGroupDTO>> getAllArticleGroups(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArticleGroups");
        Page<ArticleGroupDTO> page = articleGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-groups/:id} : get the "id" articleGroup.
     *
     * @param id the id of the articleGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/QLNVB/{id}")
    public ResponseEntity<ArticleGroupDTO> getArticleGroup(@PathVariable("id") Long id) {
        log.debug("REST request to get ArticleGroup : {}", id);
        Optional<ArticleGroupDTO> articleGroupDTO = articleGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleGroupDTO);
    }

    /**
     * {@code DELETE  /article-groups/:id} : delete the "id" articleGroup.
     *
     * @param id the id of the articleGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/QLNVB/{id}")
    public ResponseEntity<Void> deleteArticleGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArticleGroup : {}", id);
        articleGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/QLNVB/search/list-name-group")
    public ServiceResult<List<Map<String, Object>>> getListArticleGroupName() {
        return this.articleGroupService.getLstArticleGroupCodeName();
    }
}
