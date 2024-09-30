package com.lottery.app.web.rest;

import com.lottery.app.repository.FileSavesRepository;
import com.lottery.app.service.FileSavesService;
import com.lottery.app.service.dto.FileSavesDTO;
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
 * REST controller for managing {@link com.lottery.app.domain.FileSaves}.
 */
@RestController
@RequestMapping("/api/file-saves")
public class FileSavesResource {

    private final Logger log = LoggerFactory.getLogger(FileSavesResource.class);

    private static final String ENTITY_NAME = "fileSaves";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileSavesService fileSavesService;

    private final FileSavesRepository fileSavesRepository;

    public FileSavesResource(FileSavesService fileSavesService, FileSavesRepository fileSavesRepository) {
        this.fileSavesService = fileSavesService;
        this.fileSavesRepository = fileSavesRepository;
    }

    /**
     * {@code POST  /file-saves} : Create a new fileSaves.
     *
     * @param fileSavesDTO the fileSavesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileSavesDTO, or with status {@code 400 (Bad Request)} if the fileSaves has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FileSavesDTO> createFileSaves(@Valid @RequestBody FileSavesDTO fileSavesDTO) throws URISyntaxException {
        log.debug("REST request to save FileSaves : {}", fileSavesDTO);
        if (fileSavesDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileSaves cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileSavesDTO result = fileSavesService.save(fileSavesDTO);
        return ResponseEntity
            .created(new URI("/api/file-saves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-saves/:id} : Updates an existing fileSaves.
     *
     * @param id the id of the fileSavesDTO to save.
     * @param fileSavesDTO the fileSavesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileSavesDTO,
     * or with status {@code 400 (Bad Request)} if the fileSavesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileSavesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FileSavesDTO> updateFileSaves(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FileSavesDTO fileSavesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FileSaves : {}, {}", id, fileSavesDTO);
        if (fileSavesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileSavesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileSavesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileSavesDTO result = fileSavesService.update(fileSavesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileSavesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-saves/:id} : Partial updates given fields of an existing fileSaves, field will ignore if it is null
     *
     * @param id the id of the fileSavesDTO to save.
     * @param fileSavesDTO the fileSavesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileSavesDTO,
     * or with status {@code 400 (Bad Request)} if the fileSavesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileSavesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileSavesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileSavesDTO> partialUpdateFileSaves(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FileSavesDTO fileSavesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileSaves partially : {}, {}", id, fileSavesDTO);
        if (fileSavesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileSavesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileSavesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileSavesDTO> result = fileSavesService.partialUpdate(fileSavesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileSavesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /file-saves} : get all the fileSaves.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileSaves in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FileSavesDTO>> getAllFileSaves(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FileSaves");
        Page<FileSavesDTO> page = fileSavesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-saves/:id} : get the "id" fileSaves.
     *
     * @param id the id of the fileSavesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileSavesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FileSavesDTO> getFileSaves(@PathVariable("id") Long id) {
        log.debug("REST request to get FileSaves : {}", id);
        Optional<FileSavesDTO> fileSavesDTO = fileSavesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileSavesDTO);
    }

    /**
     * {@code DELETE  /file-saves/:id} : delete the "id" fileSaves.
     *
     * @param id the id of the fileSavesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFileSaves(@PathVariable("id") Long id) {
        log.debug("REST request to delete FileSaves : {}", id);
        fileSavesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
