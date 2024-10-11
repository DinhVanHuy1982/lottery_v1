package com.lottery.app.web.rest;

import com.lottery.app.repository.LevelDepositsRepository;
import com.lottery.app.service.LevelDepositsService;
import com.lottery.app.service.dto.LevelDepositsDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.LevelDeposits}.
 */
@RestController
@RequestMapping("/api/level-deposits")
public class LevelDepositsResource {

    private final Logger log = LoggerFactory.getLogger(LevelDepositsResource.class);

    private static final String ENTITY_NAME = "levelDeposits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelDepositsService levelDepositsService;

    private final LevelDepositsRepository levelDepositsRepository;

    public LevelDepositsResource(LevelDepositsService levelDepositsService, LevelDepositsRepository levelDepositsRepository) {
        this.levelDepositsService = levelDepositsService;
        this.levelDepositsRepository = levelDepositsRepository;
    }

    /**
     * {@code POST  /level-deposits} : Create a new levelDeposits.
     *
     * @param levelDepositsDTO the levelDepositsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelDepositsDTO, or with status {@code 400 (Bad Request)} if the levelDeposits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LevelDepositsDTO> createLevelDeposits(@Valid @RequestBody LevelDepositsDTO levelDepositsDTO)
        throws URISyntaxException {
        log.debug("REST request to save LevelDeposits : {}", levelDepositsDTO);
        if (levelDepositsDTO.getId() != null) {
            throw new BadRequestAlertException("A new levelDeposits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelDepositsDTO result = levelDepositsService.save(levelDepositsDTO);
        return ResponseEntity
            .created(new URI("/api/level-deposits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-deposits/:id} : Updates an existing levelDeposits.
     *
     * @param id the id of the levelDepositsDTO to save.
     * @param levelDepositsDTO the levelDepositsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelDepositsDTO,
     * or with status {@code 400 (Bad Request)} if the levelDepositsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelDepositsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LevelDepositsDTO> updateLevelDeposits(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LevelDepositsDTO levelDepositsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LevelDeposits : {}, {}", id, levelDepositsDTO);
        if (levelDepositsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelDepositsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelDepositsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LevelDepositsDTO result = levelDepositsService.update(levelDepositsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelDepositsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /level-deposits/:id} : Partial updates given fields of an existing levelDeposits, field will ignore if it is null
     *
     * @param id the id of the levelDepositsDTO to save.
     * @param levelDepositsDTO the levelDepositsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelDepositsDTO,
     * or with status {@code 400 (Bad Request)} if the levelDepositsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the levelDepositsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the levelDepositsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LevelDepositsDTO> partialUpdateLevelDeposits(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LevelDepositsDTO levelDepositsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LevelDeposits partially : {}, {}", id, levelDepositsDTO);
        if (levelDepositsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levelDepositsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!levelDepositsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LevelDepositsDTO> result = levelDepositsService.partialUpdate(levelDepositsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelDepositsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /level-deposits} : get all the levelDeposits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelDeposits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LevelDepositsDTO>> getAllLevelDeposits(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LevelDeposits");
        Page<LevelDepositsDTO> page = levelDepositsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /level-deposits/:id} : get the "id" levelDeposits.
     *
     * @param id the id of the levelDepositsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelDepositsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LevelDepositsDTO> getLevelDeposits(@PathVariable("id") Long id) {
        log.debug("REST request to get LevelDeposits : {}", id);
        Optional<LevelDepositsDTO> levelDepositsDTO = levelDepositsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelDepositsDTO);
    }

    /**
     * {@code DELETE  /level-deposits/:id} : delete the "id" levelDeposits.
     *
     * @param id the id of the levelDepositsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLevelDeposits(@PathVariable("id") Long id) {
        log.debug("REST request to delete LevelDeposits : {}", id);
        levelDepositsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
