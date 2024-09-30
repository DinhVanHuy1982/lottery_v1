package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.FileSaves;
import com.lottery.app.repository.FileSavesRepository;
import com.lottery.app.service.dto.FileSavesDTO;
import com.lottery.app.service.mapper.FileSavesMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FileSavesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileSavesResourceIT {

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/file-saves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileSavesRepository fileSavesRepository;

    @Autowired
    private FileSavesMapper fileSavesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileSavesMockMvc;

    private FileSaves fileSaves;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSaves createEntity(EntityManager em) {
        FileSaves fileSaves = new FileSaves().fileId(DEFAULT_FILE_ID).fileName(DEFAULT_FILE_NAME).filePath(DEFAULT_FILE_PATH);
        return fileSaves;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSaves createUpdatedEntity(EntityManager em) {
        FileSaves fileSaves = new FileSaves().fileId(UPDATED_FILE_ID).fileName(UPDATED_FILE_NAME).filePath(UPDATED_FILE_PATH);
        return fileSaves;
    }

    @BeforeEach
    public void initTest() {
        fileSaves = createEntity(em);
    }

    @Test
    @Transactional
    void createFileSaves() throws Exception {
        int databaseSizeBeforeCreate = fileSavesRepository.findAll().size();
        // Create the FileSaves
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);
        restFileSavesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSavesDTO)))
            .andExpect(status().isCreated());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeCreate + 1);
        FileSaves testFileSaves = fileSavesList.get(fileSavesList.size() - 1);
        assertThat(testFileSaves.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testFileSaves.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileSaves.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
    }

    @Test
    @Transactional
    void createFileSavesWithExistingId() throws Exception {
        // Create the FileSaves with an existing ID
        fileSaves.setId(1L);
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);

        int databaseSizeBeforeCreate = fileSavesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileSavesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSavesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFileSaves() throws Exception {
        // Initialize the database
        fileSavesRepository.saveAndFlush(fileSaves);

        // Get all the fileSavesList
        restFileSavesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileSaves.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)));
    }

    @Test
    @Transactional
    void getFileSaves() throws Exception {
        // Initialize the database
        fileSavesRepository.saveAndFlush(fileSaves);

        // Get the fileSaves
        restFileSavesMockMvc
            .perform(get(ENTITY_API_URL_ID, fileSaves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileSaves.getId().intValue()))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH));
    }

    @Test
    @Transactional
    void getNonExistingFileSaves() throws Exception {
        // Get the fileSaves
        restFileSavesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFileSaves() throws Exception {
        // Initialize the database
        fileSavesRepository.saveAndFlush(fileSaves);

        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();

        // Update the fileSaves
        FileSaves updatedFileSaves = fileSavesRepository.findById(fileSaves.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFileSaves are not directly saved in db
        em.detach(updatedFileSaves);
        updatedFileSaves.fileId(UPDATED_FILE_ID).fileName(UPDATED_FILE_NAME).filePath(UPDATED_FILE_PATH);
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(updatedFileSaves);

        restFileSavesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileSavesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileSavesDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
        FileSaves testFileSaves = fileSavesList.get(fileSavesList.size() - 1);
        assertThat(testFileSaves.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testFileSaves.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileSaves.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void putNonExistingFileSaves() throws Exception {
        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();
        fileSaves.setId(longCount.incrementAndGet());

        // Create the FileSaves
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileSavesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileSavesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileSavesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileSaves() throws Exception {
        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();
        fileSaves.setId(longCount.incrementAndGet());

        // Create the FileSaves
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSavesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileSavesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileSaves() throws Exception {
        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();
        fileSaves.setId(longCount.incrementAndGet());

        // Create the FileSaves
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSavesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSavesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileSavesWithPatch() throws Exception {
        // Initialize the database
        fileSavesRepository.saveAndFlush(fileSaves);

        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();

        // Update the fileSaves using partial update
        FileSaves partialUpdatedFileSaves = new FileSaves();
        partialUpdatedFileSaves.setId(fileSaves.getId());

        partialUpdatedFileSaves.fileName(UPDATED_FILE_NAME);

        restFileSavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileSaves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileSaves))
            )
            .andExpect(status().isOk());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
        FileSaves testFileSaves = fileSavesList.get(fileSavesList.size() - 1);
        assertThat(testFileSaves.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testFileSaves.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileSaves.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
    }

    @Test
    @Transactional
    void fullUpdateFileSavesWithPatch() throws Exception {
        // Initialize the database
        fileSavesRepository.saveAndFlush(fileSaves);

        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();

        // Update the fileSaves using partial update
        FileSaves partialUpdatedFileSaves = new FileSaves();
        partialUpdatedFileSaves.setId(fileSaves.getId());

        partialUpdatedFileSaves.fileId(UPDATED_FILE_ID).fileName(UPDATED_FILE_NAME).filePath(UPDATED_FILE_PATH);

        restFileSavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileSaves.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileSaves))
            )
            .andExpect(status().isOk());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
        FileSaves testFileSaves = fileSavesList.get(fileSavesList.size() - 1);
        assertThat(testFileSaves.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testFileSaves.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileSaves.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingFileSaves() throws Exception {
        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();
        fileSaves.setId(longCount.incrementAndGet());

        // Create the FileSaves
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileSavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileSavesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileSavesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileSaves() throws Exception {
        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();
        fileSaves.setId(longCount.incrementAndGet());

        // Create the FileSaves
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSavesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileSavesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileSaves() throws Exception {
        int databaseSizeBeforeUpdate = fileSavesRepository.findAll().size();
        fileSaves.setId(longCount.incrementAndGet());

        // Create the FileSaves
        FileSavesDTO fileSavesDTO = fileSavesMapper.toDto(fileSaves);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSavesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileSavesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileSaves in the database
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileSaves() throws Exception {
        // Initialize the database
        fileSavesRepository.saveAndFlush(fileSaves);

        int databaseSizeBeforeDelete = fileSavesRepository.findAll().size();

        // Delete the fileSaves
        restFileSavesMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileSaves.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileSaves> fileSavesList = fileSavesRepository.findAll();
        assertThat(fileSavesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
