package com.lottery.app.web.rest;

import com.lottery.app.repository.DepositsRepository;
import com.lottery.app.service.DepositsService;
import com.lottery.app.service.dto.DepositsDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.Deposits}.
 */
@RestController
@RequestMapping("/api/deposits")
public class DepositsResource {

    private final Logger log = LoggerFactory.getLogger(DepositsResource.class);

    private static final String ENTITY_NAME = "deposits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepositsService depositsService;

    private final DepositsRepository depositsRepository;

    public DepositsResource(DepositsService depositsService, DepositsRepository depositsRepository) {
        this.depositsService = depositsService;
        this.depositsRepository = depositsRepository;
    }

    /**
     * {@code POST  /deposits} : Create a new deposits.
     *
     * @param depositsDTO the depositsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depositsDTO, or with status {@code 400 (Bad Request)} if the deposits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DepositsDTO> createDeposits(@Valid @RequestBody DepositsDTO depositsDTO) throws URISyntaxException {
        log.debug("REST request to save Deposits : {}", depositsDTO);
        if (depositsDTO.getId() != null) {
            throw new BadRequestAlertException("A new deposits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositsDTO result = depositsService.save(depositsDTO);
        return ResponseEntity
            .created(new URI("/api/deposits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deposits/:id} : Updates an existing deposits.
     *
     * @param id the id of the depositsDTO to save.
     * @param depositsDTO the depositsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depositsDTO,
     * or with status {@code 400 (Bad Request)} if the depositsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depositsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepositsDTO> updateDeposits(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepositsDTO depositsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Deposits : {}, {}", id, depositsDTO);
        if (depositsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depositsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depositsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DepositsDTO result = depositsService.update(depositsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depositsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deposits/:id} : Partial updates given fields of an existing deposits, field will ignore if it is null
     *
     * @param id the id of the depositsDTO to save.
     * @param depositsDTO the depositsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depositsDTO,
     * or with status {@code 400 (Bad Request)} if the depositsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the depositsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the depositsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepositsDTO> partialUpdateDeposits(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepositsDTO depositsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deposits partially : {}, {}", id, depositsDTO);
        if (depositsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depositsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depositsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepositsDTO> result = depositsService.partialUpdate(depositsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depositsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /deposits} : get all the deposits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deposits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DepositsDTO>> getAllDeposits(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Deposits");
        Page<DepositsDTO> page = depositsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deposits/:id} : get the "id" deposits.
     *
     * @param id the id of the depositsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depositsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepositsDTO> getDeposits(@PathVariable("id") Long id) {
        log.debug("REST request to get Deposits : {}", id);
        Optional<DepositsDTO> depositsDTO = depositsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depositsDTO);
    }

    /**
     * {@code DELETE  /deposits/:id} : delete the "id" deposits.
     *
     * @param id the id of the depositsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeposits(@PathVariable("id") Long id) {
        log.debug("REST request to delete Deposits : {}", id);
        depositsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
