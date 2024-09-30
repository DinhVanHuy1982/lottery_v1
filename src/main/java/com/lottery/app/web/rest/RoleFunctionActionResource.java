package com.lottery.app.web.rest;

import com.lottery.app.repository.RoleFunctionActionRepository;
import com.lottery.app.service.RoleFunctionActionService;
import com.lottery.app.service.dto.RoleFunctionActionDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.RoleFunctionAction}.
 */
@RestController
@RequestMapping("/api/role-function-actions")
public class RoleFunctionActionResource {

    private final Logger log = LoggerFactory.getLogger(RoleFunctionActionResource.class);

    private static final String ENTITY_NAME = "roleFunctionAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleFunctionActionService roleFunctionActionService;

    private final RoleFunctionActionRepository roleFunctionActionRepository;

    public RoleFunctionActionResource(
        RoleFunctionActionService roleFunctionActionService,
        RoleFunctionActionRepository roleFunctionActionRepository
    ) {
        this.roleFunctionActionService = roleFunctionActionService;
        this.roleFunctionActionRepository = roleFunctionActionRepository;
    }

    /**
     * {@code POST  /role-function-actions} : Create a new roleFunctionAction.
     *
     * @param roleFunctionActionDTO the roleFunctionActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleFunctionActionDTO, or with status {@code 400 (Bad Request)} if the roleFunctionAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RoleFunctionActionDTO> createRoleFunctionAction(@Valid @RequestBody RoleFunctionActionDTO roleFunctionActionDTO)
        throws URISyntaxException {
        log.debug("REST request to save RoleFunctionAction : {}", roleFunctionActionDTO);
        if (roleFunctionActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleFunctionAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleFunctionActionDTO result = roleFunctionActionService.save(roleFunctionActionDTO);
        return ResponseEntity
            .created(new URI("/api/role-function-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-function-actions/:id} : Updates an existing roleFunctionAction.
     *
     * @param id the id of the roleFunctionActionDTO to save.
     * @param roleFunctionActionDTO the roleFunctionActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleFunctionActionDTO,
     * or with status {@code 400 (Bad Request)} if the roleFunctionActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleFunctionActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoleFunctionActionDTO> updateRoleFunctionAction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoleFunctionActionDTO roleFunctionActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleFunctionAction : {}, {}", id, roleFunctionActionDTO);
        if (roleFunctionActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleFunctionActionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleFunctionActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleFunctionActionDTO result = roleFunctionActionService.update(roleFunctionActionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleFunctionActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-function-actions/:id} : Partial updates given fields of an existing roleFunctionAction, field will ignore if it is null
     *
     * @param id the id of the roleFunctionActionDTO to save.
     * @param roleFunctionActionDTO the roleFunctionActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleFunctionActionDTO,
     * or with status {@code 400 (Bad Request)} if the roleFunctionActionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleFunctionActionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleFunctionActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleFunctionActionDTO> partialUpdateRoleFunctionAction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoleFunctionActionDTO roleFunctionActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleFunctionAction partially : {}, {}", id, roleFunctionActionDTO);
        if (roleFunctionActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleFunctionActionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleFunctionActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleFunctionActionDTO> result = roleFunctionActionService.partialUpdate(roleFunctionActionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleFunctionActionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /role-function-actions} : get all the roleFunctionActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleFunctionActions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RoleFunctionActionDTO>> getAllRoleFunctionActions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RoleFunctionActions");
        Page<RoleFunctionActionDTO> page = roleFunctionActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-function-actions/:id} : get the "id" roleFunctionAction.
     *
     * @param id the id of the roleFunctionActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleFunctionActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleFunctionActionDTO> getRoleFunctionAction(@PathVariable("id") Long id) {
        log.debug("REST request to get RoleFunctionAction : {}", id);
        Optional<RoleFunctionActionDTO> roleFunctionActionDTO = roleFunctionActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleFunctionActionDTO);
    }

    /**
     * {@code DELETE  /role-function-actions/:id} : delete the "id" roleFunctionAction.
     *
     * @param id the id of the roleFunctionActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleFunctionAction(@PathVariable("id") Long id) {
        log.debug("REST request to delete RoleFunctionAction : {}", id);
        roleFunctionActionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
