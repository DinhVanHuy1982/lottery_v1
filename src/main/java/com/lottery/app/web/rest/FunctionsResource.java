package com.lottery.app.web.rest;

import com.lottery.app.repository.FunctionsRepository;
import com.lottery.app.service.FunctionsService;
import com.lottery.app.service.dto.FunctionsDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.Functions}.
 */
@RestController
@RequestMapping("/api/functions")
public class FunctionsResource {

    private final Logger log = LoggerFactory.getLogger(FunctionsResource.class);

    private static final String ENTITY_NAME = "functions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctionsService functionsService;

    private final FunctionsRepository functionsRepository;

    public FunctionsResource(FunctionsService functionsService, FunctionsRepository functionsRepository) {
        this.functionsService = functionsService;
        this.functionsRepository = functionsRepository;
    }

    /**
     * {@code POST  /functions} : Create a new functions.
     *
     * @param functionsDTO the functionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functionsDTO, or with status {@code 400 (Bad Request)} if the functions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FunctionsDTO> createFunctions(@Valid @RequestBody FunctionsDTO functionsDTO) throws URISyntaxException {
        log.debug("REST request to save Functions : {}", functionsDTO);
        if (functionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new functions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FunctionsDTO result = functionsService.save(functionsDTO);
        return ResponseEntity
            .created(new URI("/api/functions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /functions/:id} : Updates an existing functions.
     *
     * @param id the id of the functionsDTO to save.
     * @param functionsDTO the functionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionsDTO,
     * or with status {@code 400 (Bad Request)} if the functionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FunctionsDTO> updateFunctions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FunctionsDTO functionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Functions : {}, {}", id, functionsDTO);
        if (functionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FunctionsDTO result = functionsService.update(functionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /functions/:id} : Partial updates given fields of an existing functions, field will ignore if it is null
     *
     * @param id the id of the functionsDTO to save.
     * @param functionsDTO the functionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionsDTO,
     * or with status {@code 400 (Bad Request)} if the functionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the functionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the functionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FunctionsDTO> partialUpdateFunctions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FunctionsDTO functionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Functions partially : {}, {}", id, functionsDTO);
        if (functionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FunctionsDTO> result = functionsService.partialUpdate(functionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /functions} : get all the functions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FunctionsDTO>> getAllFunctions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Functions");
        Page<FunctionsDTO> page = functionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /functions/:id} : get the "id" functions.
     *
     * @param id the id of the functionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FunctionsDTO> getFunctions(@PathVariable("id") Long id) {
        log.debug("REST request to get Functions : {}", id);
        Optional<FunctionsDTO> functionsDTO = functionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(functionsDTO);
    }

    /**
     * {@code DELETE  /functions/:id} : delete the "id" functions.
     *
     * @param id the id of the functionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFunctions(@PathVariable("id") Long id) {
        log.debug("REST request to delete Functions : {}", id);
        functionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
