package com.lottery.app.web.rest;

import com.lottery.app.repository.AppParamsRepository;
import com.lottery.app.service.AppParamsService;
import com.lottery.app.service.dto.AppParamsDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.AppParams}.
 */
@RestController
@RequestMapping("/api/app-params")
public class AppParamsResource {

    private final Logger log = LoggerFactory.getLogger(AppParamsResource.class);

    private static final String ENTITY_NAME = "appParams";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppParamsService appParamsService;

    private final AppParamsRepository appParamsRepository;

    public AppParamsResource(AppParamsService appParamsService, AppParamsRepository appParamsRepository) {
        this.appParamsService = appParamsService;
        this.appParamsRepository = appParamsRepository;
    }

    /**
     * {@code POST  /app-params} : Create a new appParams.
     *
     * @param appParamsDTO the appParamsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appParamsDTO, or with status {@code 400 (Bad Request)} if the appParams has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppParamsDTO> createAppParams(@Valid @RequestBody AppParamsDTO appParamsDTO) throws URISyntaxException {
        log.debug("REST request to save AppParams : {}", appParamsDTO);
        if (appParamsDTO.getId() != null) {
            throw new BadRequestAlertException("A new appParams cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppParamsDTO result = appParamsService.save(appParamsDTO);
        return ResponseEntity
            .created(new URI("/api/app-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-params/:id} : Updates an existing appParams.
     *
     * @param id the id of the appParamsDTO to save.
     * @param appParamsDTO the appParamsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appParamsDTO,
     * or with status {@code 400 (Bad Request)} if the appParamsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appParamsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppParamsDTO> updateAppParams(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppParamsDTO appParamsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppParams : {}, {}", id, appParamsDTO);
        if (appParamsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appParamsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appParamsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppParamsDTO result = appParamsService.update(appParamsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appParamsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-params/:id} : Partial updates given fields of an existing appParams, field will ignore if it is null
     *
     * @param id the id of the appParamsDTO to save.
     * @param appParamsDTO the appParamsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appParamsDTO,
     * or with status {@code 400 (Bad Request)} if the appParamsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appParamsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appParamsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppParamsDTO> partialUpdateAppParams(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppParamsDTO appParamsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppParams partially : {}, {}", id, appParamsDTO);
        if (appParamsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appParamsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appParamsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppParamsDTO> result = appParamsService.partialUpdate(appParamsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appParamsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-params} : get all the appParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appParams in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AppParamsDTO>> getAllAppParams(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AppParams");
        Page<AppParamsDTO> page = appParamsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-params/:id} : get the "id" appParams.
     *
     * @param id the id of the appParamsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appParamsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppParamsDTO> getAppParams(@PathVariable("id") Long id) {
        log.debug("REST request to get AppParams : {}", id);
        Optional<AppParamsDTO> appParamsDTO = appParamsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appParamsDTO);
    }

    /**
     * {@code DELETE  /app-params/:id} : delete the "id" appParams.
     *
     * @param id the id of the appParamsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppParams(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppParams : {}", id);
        appParamsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
