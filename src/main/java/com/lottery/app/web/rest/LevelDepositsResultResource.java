package com.lottery.app.web.rest;

import com.lottery.app.repository.LevelDepositsResultRepository;
import com.lottery.app.service.LevelDepositsResultService;
import com.lottery.app.service.dto.LevelDepositsResultDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.LevelDepositsResult}.
 */
@RestController
@RequestMapping("/api/level-deposits-results")
public class LevelDepositsResultResource {

    private final Logger log = LoggerFactory.getLogger(LevelDepositsResultResource.class);

    private static final String ENTITY_NAME = "levelDepositsResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelDepositsResultService levelDepositsResultService;

    private final LevelDepositsResultRepository levelDepositsResultRepository;

    public LevelDepositsResultResource(
        LevelDepositsResultService levelDepositsResultService,
        LevelDepositsResultRepository levelDepositsResultRepository
    ) {
        this.levelDepositsResultService = levelDepositsResultService;
        this.levelDepositsResultRepository = levelDepositsResultRepository;
    }

    /**
     * {@code POST  /level-deposits-results} : Create a new levelDepositsResult.
     *
     * @param levelDepositsResultDTO the levelDepositsResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelDepositsResultDTO, or with status {@code 400 (Bad Request)} if the levelDepositsResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LevelDepositsResultDTO> createLevelDepositsResult(
        @Valid @RequestBody LevelDepositsResultDTO levelDepositsResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to save LevelDepositsResult : {}", levelDepositsResultDTO);
        if (levelDepositsResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new levelDepositsResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelDepositsResultDTO result = levelDepositsResultService.save(levelDepositsResultDTO);
        return ResponseEntity
            .created(new URI("/api/level-deposits-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-deposits-results/:id} : Updates an existing levelDepositsResult.
     *
     * @param id the id of the levelDepositsResultDTO to save.
     * @param levelDepositsResultDTO the levelDepositsResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelDepositsResultDTO,
     * or with status {@code 400 (Bad Request)} if the levelDepositsResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelDepositsResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LevelDepositsResultDTO> updateLevelDepositsResult(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelDepositsResultDTO levelDepositsResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LevelDepositsResult : {}, {}", id, levelDepositsResultDTO);
        if (levelDepositsResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelDepositsResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelDepositsResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelDepositsResultDTO result = levelDepositsResultService.update(levelDepositsResultDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelDepositsResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /level-deposits-results/:id} : Partial updates given fields of an existing levelDepositsResult, field will ignore if it is null
     *
     * @param id the id of the levelDepositsResultDTO to save.
     * @param levelDepositsResultDTO the levelDepositsResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelDepositsResultDTO,
     * or with status {@code 400 (Bad Request)} if the levelDepositsResultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the levelDepositsResultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelDepositsResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LevelDepositsResultDTO> partialUpdateLevelDepositsResult(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelDepositsResultDTO levelDepositsResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LevelDepositsResult partially : {}, {}", id, levelDepositsResultDTO);
        if (levelDepositsResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelDepositsResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelDepositsResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelDepositsResultDTO> result = levelDepositsResultService.partialUpdate(levelDepositsResultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelDepositsResultDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /level-deposits-results} : get all the levelDepositsResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelDepositsResults in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LevelDepositsResultDTO>> getAllLevelDepositsResults(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LevelDepositsResults");
        Page<LevelDepositsResultDTO> page = levelDepositsResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /level-deposits-results/:id} : get the "id" levelDepositsResult.
     *
     * @param id the id of the levelDepositsResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelDepositsResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LevelDepositsResultDTO> getLevelDepositsResult(@PathVariable("id") Long id) {
        log.debug("REST request to get LevelDepositsResult : {}", id);
        Optional<LevelDepositsResultDTO> levelDepositsResultDTO = levelDepositsResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelDepositsResultDTO);
    }

    /**
     * {@code DELETE  /level-deposits-results/:id} : delete the "id" levelDepositsResult.
     *
     * @param id the id of the levelDepositsResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLevelDepositsResult(@PathVariable("id") Long id) {
        log.debug("REST request to delete LevelDepositsResult : {}", id);
        levelDepositsResultService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
