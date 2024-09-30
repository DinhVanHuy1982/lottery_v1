package com.lottery.app.web.rest;

import com.lottery.app.repository.IntroduceArticleGroupRepository;
import com.lottery.app.service.IntroduceArticleGroupService;
import com.lottery.app.service.dto.IntroduceArticleGroupDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.IntroduceArticleGroup}.
 */
@RestController
@RequestMapping("/api/introduce-article-groups")
public class IntroduceArticleGroupResource {

    private final Logger log = LoggerFactory.getLogger(IntroduceArticleGroupResource.class);

    private static final String ENTITY_NAME = "introduceArticleGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntroduceArticleGroupService introduceArticleGroupService;

    private final IntroduceArticleGroupRepository introduceArticleGroupRepository;

    public IntroduceArticleGroupResource(
        IntroduceArticleGroupService introduceArticleGroupService,
        IntroduceArticleGroupRepository introduceArticleGroupRepository
    ) {
        this.introduceArticleGroupService = introduceArticleGroupService;
        this.introduceArticleGroupRepository = introduceArticleGroupRepository;
    }

    /**
     * {@code POST  /introduce-article-groups} : Create a new introduceArticleGroup.
     *
     * @param introduceArticleGroupDTO the introduceArticleGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new introduceArticleGroupDTO, or with status {@code 400 (Bad Request)} if the introduceArticleGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntroduceArticleGroupDTO> createIntroduceArticleGroup(
        @Valid @RequestBody IntroduceArticleGroupDTO introduceArticleGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to save IntroduceArticleGroup : {}", introduceArticleGroupDTO);
        if (introduceArticleGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new introduceArticleGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntroduceArticleGroupDTO result = introduceArticleGroupService.save(introduceArticleGroupDTO);
        return ResponseEntity
            .created(new URI("/api/introduce-article-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /introduce-article-groups/:id} : Updates an existing introduceArticleGroup.
     *
     * @param id the id of the introduceArticleGroupDTO to save.
     * @param introduceArticleGroupDTO the introduceArticleGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated introduceArticleGroupDTO,
     * or with status {@code 400 (Bad Request)} if the introduceArticleGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the introduceArticleGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntroduceArticleGroupDTO> updateIntroduceArticleGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntroduceArticleGroupDTO introduceArticleGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IntroduceArticleGroup : {}, {}", id, introduceArticleGroupDTO);
        if (introduceArticleGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, introduceArticleGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!introduceArticleGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntroduceArticleGroupDTO result = introduceArticleGroupService.update(introduceArticleGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, introduceArticleGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /introduce-article-groups/:id} : Partial updates given fields of an existing introduceArticleGroup, field will ignore if it is null
     *
     * @param id the id of the introduceArticleGroupDTO to save.
     * @param introduceArticleGroupDTO the introduceArticleGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated introduceArticleGroupDTO,
     * or with status {@code 400 (Bad Request)} if the introduceArticleGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the introduceArticleGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the introduceArticleGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntroduceArticleGroupDTO> partialUpdateIntroduceArticleGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntroduceArticleGroupDTO introduceArticleGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntroduceArticleGroup partially : {}, {}", id, introduceArticleGroupDTO);
        if (introduceArticleGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, introduceArticleGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!introduceArticleGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntroduceArticleGroupDTO> result = introduceArticleGroupService.partialUpdate(introduceArticleGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, introduceArticleGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /introduce-article-groups} : get all the introduceArticleGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of introduceArticleGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<IntroduceArticleGroupDTO>> getAllIntroduceArticleGroups(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of IntroduceArticleGroups");
        Page<IntroduceArticleGroupDTO> page = introduceArticleGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /introduce-article-groups/:id} : get the "id" introduceArticleGroup.
     *
     * @param id the id of the introduceArticleGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the introduceArticleGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntroduceArticleGroupDTO> getIntroduceArticleGroup(@PathVariable("id") Long id) {
        log.debug("REST request to get IntroduceArticleGroup : {}", id);
        Optional<IntroduceArticleGroupDTO> introduceArticleGroupDTO = introduceArticleGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(introduceArticleGroupDTO);
    }

    /**
     * {@code DELETE  /introduce-article-groups/:id} : delete the "id" introduceArticleGroup.
     *
     * @param id the id of the introduceArticleGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntroduceArticleGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete IntroduceArticleGroup : {}", id);
        introduceArticleGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
