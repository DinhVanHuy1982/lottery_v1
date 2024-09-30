package com.lottery.app.web.rest;

import com.lottery.app.repository.RoleFunctionsRepository;
import com.lottery.app.service.RoleFunctionsService;
import com.lottery.app.service.dto.RoleFunctionsDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.RoleFunctions}.
 */
@RestController
@RequestMapping("/api/role-functions")
public class RoleFunctionsResource {

    private final Logger log = LoggerFactory.getLogger(RoleFunctionsResource.class);

    private static final String ENTITY_NAME = "roleFunctions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleFunctionsService roleFunctionsService;

    private final RoleFunctionsRepository roleFunctionsRepository;

    public RoleFunctionsResource(RoleFunctionsService roleFunctionsService, RoleFunctionsRepository roleFunctionsRepository) {
        this.roleFunctionsService = roleFunctionsService;
        this.roleFunctionsRepository = roleFunctionsRepository;
    }

    /**
     * {@code POST  /role-functions} : Create a new roleFunctions.
     *
     * @param roleFunctionsDTO the roleFunctionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleFunctionsDTO, or with status {@code 400 (Bad Request)} if the roleFunctions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RoleFunctionsDTO> createRoleFunctions(@Valid @RequestBody RoleFunctionsDTO roleFunctionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save RoleFunctions : {}", roleFunctionsDTO);
        if (roleFunctionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleFunctions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleFunctionsDTO result = roleFunctionsService.save(roleFunctionsDTO);
        return ResponseEntity
            .created(new URI("/api/role-functions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-functions/:id} : Updates an existing roleFunctions.
     *
     * @param id the id of the roleFunctionsDTO to save.
     * @param roleFunctionsDTO the roleFunctionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleFunctionsDTO,
     * or with status {@code 400 (Bad Request)} if the roleFunctionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleFunctionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoleFunctionsDTO> updateRoleFunctions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoleFunctionsDTO roleFunctionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleFunctions : {}, {}", id, roleFunctionsDTO);
        if (roleFunctionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleFunctionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleFunctionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleFunctionsDTO result = roleFunctionsService.update(roleFunctionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleFunctionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-functions/:id} : Partial updates given fields of an existing roleFunctions, field will ignore if it is null
     *
     * @param id the id of the roleFunctionsDTO to save.
     * @param roleFunctionsDTO the roleFunctionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleFunctionsDTO,
     * or with status {@code 400 (Bad Request)} if the roleFunctionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleFunctionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleFunctionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleFunctionsDTO> partialUpdateRoleFunctions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoleFunctionsDTO roleFunctionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleFunctions partially : {}, {}", id, roleFunctionsDTO);
        if (roleFunctionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleFunctionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleFunctionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleFunctionsDTO> result = roleFunctionsService.partialUpdate(roleFunctionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleFunctionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /role-functions} : get all the roleFunctions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleFunctions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RoleFunctionsDTO>> getAllRoleFunctions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RoleFunctions");
        Page<RoleFunctionsDTO> page = roleFunctionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-functions/:id} : get the "id" roleFunctions.
     *
     * @param id the id of the roleFunctionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleFunctionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleFunctionsDTO> getRoleFunctions(@PathVariable("id") Long id) {
        log.debug("REST request to get RoleFunctions : {}", id);
        Optional<RoleFunctionsDTO> roleFunctionsDTO = roleFunctionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleFunctionsDTO);
    }

    /**
     * {@code DELETE  /role-functions/:id} : delete the "id" roleFunctions.
     *
     * @param id the id of the roleFunctionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleFunctions(@PathVariable("id") Long id) {
        log.debug("REST request to delete RoleFunctions : {}", id);
        roleFunctionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
