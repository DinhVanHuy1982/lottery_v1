package com.lottery.app.service;

import com.lottery.app.service.dto.FileSavesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.lottery.app.domain.FileSaves}.
 */
public interface FileSavesService {
    /**
     * Save a fileSaves.
     *
     * @param fileSavesDTO the entity to save.
     * @return the persisted entity.
     */
    FileSavesDTO save(FileSavesDTO fileSavesDTO);

    /**
     * Updates a fileSaves.
     *
     * @param fileSavesDTO the entity to update.
     * @return the persisted entity.
     */
    FileSavesDTO update(FileSavesDTO fileSavesDTO);

    /**
     * Partially updates a fileSaves.
     *
     * @param fileSavesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileSavesDTO> partialUpdate(FileSavesDTO fileSavesDTO);

    /**
     * Get all the fileSaves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileSavesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileSaves.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileSavesDTO> findOne(Long id);

    /**
     * Delete the "id" fileSaves.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
