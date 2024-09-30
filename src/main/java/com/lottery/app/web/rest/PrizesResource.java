package com.lottery.app.web.rest;

import com.lottery.app.repository.PrizesRepository;
import com.lottery.app.service.PrizesService;
import com.lottery.app.service.dto.PrizesDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.Prizes}.
 */
@RestController
@RequestMapping("/api/prizes")
public class PrizesResource {

    private final Logger log = LoggerFactory.getLogger(PrizesResource.class);

    private static final String ENTITY_NAME = "prizes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrizesService prizesService;

    private final PrizesRepository prizesRepository;

    public PrizesResource(PrizesService prizesService, PrizesRepository prizesRepository) {
        this.prizesService = prizesService;
        this.prizesRepository = prizesRepository;
    }

    /**
     * {@code POST  /prizes} : Create a new prizes.
     *
     * @param prizesDTO the prizesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prizesDTO, or with status {@code 400 (Bad Request)} if the prizes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PrizesDTO> createPrizes(@Valid @RequestBody PrizesDTO prizesDTO) throws URISyntaxException {
        log.debug("REST request to save Prizes : {}", prizesDTO);
        if (prizesDTO.getId() != null) {
            throw new BadRequestAlertException("A new prizes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrizesDTO result = prizesService.save(prizesDTO);
        return ResponseEntity
            .created(new URI("/api/prizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prizes/:id} : Updates an existing prizes.
     *
     * @param id the id of the prizesDTO to save.
     * @param prizesDTO the prizesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prizesDTO,
     * or with status {@code 400 (Bad Request)} if the prizesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prizesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrizesDTO> updatePrizes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PrizesDTO prizesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Prizes : {}, {}", id, prizesDTO);
        if (prizesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prizesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prizesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrizesDTO result = prizesService.update(prizesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prizesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /prizes/:id} : Partial updates given fields of an existing prizes, field will ignore if it is null
     *
     * @param id the id of the prizesDTO to save.
     * @param prizesDTO the prizesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prizesDTO,
     * or with status {@code 400 (Bad Request)} if the prizesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prizesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the prizesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrizesDTO> partialUpdatePrizes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PrizesDTO prizesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prizes partially : {}, {}", id, prizesDTO);
        if (prizesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prizesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prizesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrizesDTO> result = prizesService.partialUpdate(prizesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prizesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prizes} : get all the prizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prizes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PrizesDTO>> getAllPrizes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Prizes");
        Page<PrizesDTO> page = prizesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prizes/:id} : get the "id" prizes.
     *
     * @param id the id of the prizesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prizesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrizesDTO> getPrizes(@PathVariable("id") Long id) {
        log.debug("REST request to get Prizes : {}", id);
        Optional<PrizesDTO> prizesDTO = prizesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prizesDTO);
    }

    /**
     * {@code DELETE  /prizes/:id} : delete the "id" prizes.
     *
     * @param id the id of the prizesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrizes(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prizes : {}", id);
        prizesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
