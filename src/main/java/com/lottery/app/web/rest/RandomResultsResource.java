package com.lottery.app.web.rest;

import com.lottery.app.repository.RandomResultsRepository;
import com.lottery.app.service.RandomResultsService;
import com.lottery.app.service.dto.RandomResultsDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.RandomResults}.
 */
@RestController
@RequestMapping("/api/random-results")
public class RandomResultsResource {

    private final Logger log = LoggerFactory.getLogger(RandomResultsResource.class);

    private static final String ENTITY_NAME = "randomResults";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RandomResultsService randomResultsService;

    private final RandomResultsRepository randomResultsRepository;

    public RandomResultsResource(RandomResultsService randomResultsService, RandomResultsRepository randomResultsRepository) {
        this.randomResultsService = randomResultsService;
        this.randomResultsRepository = randomResultsRepository;
    }

    /**
     * {@code POST  /random-results} : Create a new randomResults.
     *
     * @param randomResultsDTO the randomResultsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new randomResultsDTO, or with status {@code 400 (Bad Request)} if the randomResults has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RandomResultsDTO> createRandomResults(@Valid @RequestBody RandomResultsDTO randomResultsDTO)
        throws URISyntaxException {
        log.debug("REST request to save RandomResults : {}", randomResultsDTO);
        if (randomResultsDTO.getId() != null) {
            throw new BadRequestAlertException("A new randomResults cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RandomResultsDTO result = randomResultsService.save(randomResultsDTO);
        return ResponseEntity
            .created(new URI("/api/random-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /random-results/:id} : Updates an existing randomResults.
     *
     * @param id the id of the randomResultsDTO to save.
     * @param randomResultsDTO the randomResultsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated randomResultsDTO,
     * or with status {@code 400 (Bad Request)} if the randomResultsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the randomResultsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RandomResultsDTO> updateRandomResults(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RandomResultsDTO randomResultsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RandomResults : {}, {}", id, randomResultsDTO);
        if (randomResultsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, randomResultsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!randomResultsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RandomResultsDTO result = randomResultsService.update(randomResultsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, randomResultsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /random-results/:id} : Partial updates given fields of an existing randomResults, field will ignore if it is null
     *
     * @param id the id of the randomResultsDTO to save.
     * @param randomResultsDTO the randomResultsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated randomResultsDTO,
     * or with status {@code 400 (Bad Request)} if the randomResultsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the randomResultsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the randomResultsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RandomResultsDTO> partialUpdateRandomResults(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RandomResultsDTO randomResultsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RandomResults partially : {}, {}", id, randomResultsDTO);
        if (randomResultsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, randomResultsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!randomResultsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RandomResultsDTO> result = randomResultsService.partialUpdate(randomResultsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, randomResultsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /random-results} : get all the randomResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of randomResults in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RandomResultsDTO>> getAllRandomResults(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RandomResults");
        Page<RandomResultsDTO> page = randomResultsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /random-results/:id} : get the "id" randomResults.
     *
     * @param id the id of the randomResultsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the randomResultsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RandomResultsDTO> getRandomResults(@PathVariable("id") Long id) {
        log.debug("REST request to get RandomResults : {}", id);
        Optional<RandomResultsDTO> randomResultsDTO = randomResultsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(randomResultsDTO);
    }

    /**
     * {@code DELETE  /random-results/:id} : delete the "id" randomResults.
     *
     * @param id the id of the randomResultsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRandomResults(@PathVariable("id") Long id) {
        log.debug("REST request to delete RandomResults : {}", id);
        randomResultsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
