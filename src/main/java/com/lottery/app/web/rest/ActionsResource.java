package com.lottery.app.web.rest;

import com.lottery.app.repository.ActionsRepository;
import com.lottery.app.service.ActionsService;
import com.lottery.app.service.dto.ActionsDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.Actions}.
 */
@RestController
@RequestMapping("/api/actions")
public class ActionsResource {

    private final Logger log = LoggerFactory.getLogger(ActionsResource.class);

    private static final String ENTITY_NAME = "actions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionsService actionsService;

    private final ActionsRepository actionsRepository;

    public ActionsResource(ActionsService actionsService, ActionsRepository actionsRepository) {
        this.actionsService = actionsService;
        this.actionsRepository = actionsRepository;
    }

    /**
     * {@code POST  /actions} : Create a new actions.
     *
     * @param actionsDTO the actionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionsDTO, or with status {@code 400 (Bad Request)} if the actions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ActionsDTO> createActions(@Valid @RequestBody ActionsDTO actionsDTO) throws URISyntaxException {
        log.debug("REST request to save Actions : {}", actionsDTO);
        if (actionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new actions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionsDTO result = actionsService.save(actionsDTO);
        return ResponseEntity
            .created(new URI("/api/actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actions/:id} : Updates an existing actions.
     *
     * @param id the id of the actionsDTO to save.
     * @param actionsDTO the actionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionsDTO,
     * or with status {@code 400 (Bad Request)} if the actionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActionsDTO> updateActions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActionsDTO actionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Actions : {}, {}", id, actionsDTO);
        if (actionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActionsDTO result = actionsService.update(actionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /actions/:id} : Partial updates given fields of an existing actions, field will ignore if it is null
     *
     * @param id the id of the actionsDTO to save.
     * @param actionsDTO the actionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionsDTO,
     * or with status {@code 400 (Bad Request)} if the actionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the actionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the actionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActionsDTO> partialUpdateActions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActionsDTO actionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Actions partially : {}, {}", id, actionsDTO);
        if (actionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActionsDTO> result = actionsService.partialUpdate(actionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /actions} : get all the actions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ActionsDTO>> getAllActions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Actions");
        Page<ActionsDTO> page = actionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /actions/:id} : get the "id" actions.
     *
     * @param id the id of the actionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActionsDTO> getActions(@PathVariable("id") Long id) {
        log.debug("REST request to get Actions : {}", id);
        Optional<ActionsDTO> actionsDTO = actionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionsDTO);
    }

    /**
     * {@code DELETE  /actions/:id} : delete the "id" actions.
     *
     * @param id the id of the actionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActions(@PathVariable("id") Long id) {
        log.debug("REST request to delete Actions : {}", id);
        actionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
