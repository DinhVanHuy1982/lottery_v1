package com.lottery.app.web.rest;

import com.lottery.app.repository.IntroduceArticleRepository;
import com.lottery.app.service.IntroduceArticleService;
import com.lottery.app.service.dto.IntroduceArticleDTO;
import com.lottery.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
 * REST controller for managing {@link com.lottery.app.domain.IntroduceArticle}.
 */
@RestController
@RequestMapping("/api/introduce-articles")
public class IntroduceArticleResource {

    private final Logger log = LoggerFactory.getLogger(IntroduceArticleResource.class);

    private static final String ENTITY_NAME = "introduceArticle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntroduceArticleService introduceArticleService;

    private final IntroduceArticleRepository introduceArticleRepository;

    public IntroduceArticleResource(
        IntroduceArticleService introduceArticleService,
        IntroduceArticleRepository introduceArticleRepository
    ) {
        this.introduceArticleService = introduceArticleService;
        this.introduceArticleRepository = introduceArticleRepository;
    }

    /**
     * {@code POST  /introduce-articles} : Create a new introduceArticle.
     *
     * @param introduceArticleDTO the introduceArticleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new introduceArticleDTO, or with status {@code 400 (Bad Request)} if the introduceArticle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntroduceArticleDTO> createIntroduceArticle(@Valid @RequestBody IntroduceArticleDTO introduceArticleDTO)
        throws URISyntaxException {
        log.debug("REST request to save IntroduceArticle : {}", introduceArticleDTO);
        if (introduceArticleDTO.getId() != null) {
            throw new BadRequestAlertException("A new introduceArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntroduceArticleDTO result = introduceArticleService.save(introduceArticleDTO);
        return ResponseEntity
            .created(new URI("/api/introduce-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /introduce-articles/:id} : Updates an existing introduceArticle.
     *
     * @param id the id of the introduceArticleDTO to save.
     * @param introduceArticleDTO the introduceArticleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated introduceArticleDTO,
     * or with status {@code 400 (Bad Request)} if the introduceArticleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the introduceArticleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntroduceArticleDTO> updateIntroduceArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntroduceArticleDTO introduceArticleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IntroduceArticle : {}, {}", id, introduceArticleDTO);
        if (introduceArticleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, introduceArticleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!introduceArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntroduceArticleDTO result = introduceArticleService.update(introduceArticleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, introduceArticleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /introduce-articles/:id} : Partial updates given fields of an existing introduceArticle, field will ignore if it is null
     *
     * @param id the id of the introduceArticleDTO to save.
     * @param introduceArticleDTO the introduceArticleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated introduceArticleDTO,
     * or with status {@code 400 (Bad Request)} if the introduceArticleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the introduceArticleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the introduceArticleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntroduceArticleDTO> partialUpdateIntroduceArticle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntroduceArticleDTO introduceArticleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntroduceArticle partially : {}, {}", id, introduceArticleDTO);
        if (introduceArticleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, introduceArticleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!introduceArticleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntroduceArticleDTO> result = introduceArticleService.partialUpdate(introduceArticleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, introduceArticleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /introduce-articles} : get all the introduceArticles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of introduceArticles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<IntroduceArticleDTO>> getAllIntroduceArticles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of IntroduceArticles");
        Page<IntroduceArticleDTO> page = introduceArticleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /introduce-articles/:id} : get the "id" introduceArticle.
     *
     * @param id the id of the introduceArticleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the introduceArticleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntroduceArticleDTO> getIntroduceArticle(@PathVariable("id") Long id) {
        log.debug("REST request to get IntroduceArticle : {}", id);
        Optional<IntroduceArticleDTO> introduceArticleDTO = introduceArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(introduceArticleDTO);
    }

    /**
     * {@code DELETE  /introduce-articles/:id} : delete the "id" introduceArticle.
     *
     * @param id the id of the introduceArticleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntroduceArticle(@PathVariable("id") Long id) {
        log.debug("REST request to delete IntroduceArticle : {}", id);
        introduceArticleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
