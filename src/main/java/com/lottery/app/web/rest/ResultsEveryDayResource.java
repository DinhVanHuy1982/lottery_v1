package com.lottery.app.web.rest;

import com.lottery.app.repository.ResultsEveryDayRepository;
import com.lottery.app.service.ResultsEveryDayService;
import com.lottery.app.service.dto.ResultsEveryDayDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.ResultsEveryDay}.
 */
@RestController
@RequestMapping("/api/results-every-days")
public class ResultsEveryDayResource {

    private final Logger log = LoggerFactory.getLogger(ResultsEveryDayResource.class);

    private static final String ENTITY_NAME = "resultsEveryDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultsEveryDayService resultsEveryDayService;

    private final ResultsEveryDayRepository resultsEveryDayRepository;

    public ResultsEveryDayResource(ResultsEveryDayService resultsEveryDayService, ResultsEveryDayRepository resultsEveryDayRepository) {
        this.resultsEveryDayService = resultsEveryDayService;
        this.resultsEveryDayRepository = resultsEveryDayRepository;
    }

    /**
     * {@code POST  /results-every-days} : Create a new resultsEveryDay.
     *
     * @param resultsEveryDayDTO the resultsEveryDayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultsEveryDayDTO, or with status {@code 400 (Bad Request)} if the resultsEveryDay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ResultsEveryDayDTO> createResultsEveryDay(@Valid @RequestBody ResultsEveryDayDTO resultsEveryDayDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResultsEveryDay : {}", resultsEveryDayDTO);
        if (resultsEveryDayDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultsEveryDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultsEveryDayDTO result = resultsEveryDayService.save(resultsEveryDayDTO);
        return ResponseEntity
            .created(new URI("/api/results-every-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /results-every-days/:id} : Updates an existing resultsEveryDay.
     *
     * @param id the id of the resultsEveryDayDTO to save.
     * @param resultsEveryDayDTO the resultsEveryDayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultsEveryDayDTO,
     * or with status {@code 400 (Bad Request)} if the resultsEveryDayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultsEveryDayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResultsEveryDayDTO> updateResultsEveryDay(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResultsEveryDayDTO resultsEveryDayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResultsEveryDay : {}, {}", id, resultsEveryDayDTO);
        if (resultsEveryDayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultsEveryDayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultsEveryDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResultsEveryDayDTO result = resultsEveryDayService.update(resultsEveryDayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultsEveryDayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /results-every-days/:id} : Partial updates given fields of an existing resultsEveryDay, field will ignore if it is null
     *
     * @param id the id of the resultsEveryDayDTO to save.
     * @param resultsEveryDayDTO the resultsEveryDayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultsEveryDayDTO,
     * or with status {@code 400 (Bad Request)} if the resultsEveryDayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resultsEveryDayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultsEveryDayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResultsEveryDayDTO> partialUpdateResultsEveryDay(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResultsEveryDayDTO resultsEveryDayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResultsEveryDay partially : {}, {}", id, resultsEveryDayDTO);
        if (resultsEveryDayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultsEveryDayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultsEveryDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResultsEveryDayDTO> result = resultsEveryDayService.partialUpdate(resultsEveryDayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultsEveryDayDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /results-every-days} : get all the resultsEveryDays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultsEveryDays in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ResultsEveryDayDTO>> getAllResultsEveryDays(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ResultsEveryDays");
        Page<ResultsEveryDayDTO> page = resultsEveryDayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /results-every-days/:id} : get the "id" resultsEveryDay.
     *
     * @param id the id of the resultsEveryDayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultsEveryDayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultsEveryDayDTO> getResultsEveryDay(@PathVariable("id") Long id) {
        log.debug("REST request to get ResultsEveryDay : {}", id);
        Optional<ResultsEveryDayDTO> resultsEveryDayDTO = resultsEveryDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultsEveryDayDTO);
    }

    /**
     * {@code DELETE  /results-every-days/:id} : delete the "id" resultsEveryDay.
     *
     * @param id the id of the resultsEveryDayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultsEveryDay(@PathVariable("id") Long id) {
        log.debug("REST request to delete ResultsEveryDay : {}", id);
        resultsEveryDayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
