package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.Functions;
import com.lottery.app.repository.FunctionsRepository;
import com.lottery.app.service.dto.FunctionsDTO;
import com.lottery.app.service.mapper.FunctionsMapper;
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
 * Integration tests for the {@link FunctionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FunctionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/functions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FunctionsRepository functionsRepository;

    @Autowired
    private FunctionsMapper functionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctionsMockMvc;

    private Functions functions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functions createEntity(EntityManager em) {
        Functions functions = new Functions().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return functions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functions createUpdatedEntity(EntityManager em) {
        Functions functions = new Functions().name(UPDATED_NAME).code(UPDATED_CODE);
        return functions;
    }

    @BeforeEach
    public void initTest() {
        functions = createEntity(em);
    }

    @Test
    @Transactional
    void createFunctions() throws Exception {
        int databaseSizeBeforeCreate = functionsRepository.findAll().size();
        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);
        restFunctionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeCreate + 1);
        Functions testFunctions = functionsList.get(functionsList.size() - 1);
        assertThat(testFunctions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFunctions.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createFunctionsWithExistingId() throws Exception {
        // Create the Functions with an existing ID
        functions.setId(1L);
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        int databaseSizeBeforeCreate = functionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList
        restFunctionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get the functions
        restFunctionsMockMvc
            .perform(get(ENTITY_API_URL_ID, functions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingFunctions() throws Exception {
        // Get the functions
        restFunctionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();

        // Update the functions
        Functions updatedFunctions = functionsRepository.findById(functions.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFunctions are not directly saved in db
        em.detach(updatedFunctions);
        updatedFunctions.name(UPDATED_NAME).code(UPDATED_CODE);
        FunctionsDTO functionsDTO = functionsMapper.toDto(updatedFunctions);

        restFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, functionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
        Functions testFunctions = functionsList.get(functionsList.size() - 1);
        assertThat(testFunctions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFunctions.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingFunctions() throws Exception {
        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();
        functions.setId(longCount.incrementAndGet());

        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, functionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunctions() throws Exception {
        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();
        functions.setId(longCount.incrementAndGet());

        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(functionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunctions() throws Exception {
        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();
        functions.setId(longCount.incrementAndGet());

        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFunctionsWithPatch() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();

        // Update the functions using partial update
        Functions partialUpdatedFunctions = new Functions();
        partialUpdatedFunctions.setId(functions.getId());

        partialUpdatedFunctions.name(UPDATED_NAME).code(UPDATED_CODE);

        restFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctions))
            )
            .andExpect(status().isOk());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
        Functions testFunctions = functionsList.get(functionsList.size() - 1);
        assertThat(testFunctions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFunctions.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateFunctionsWithPatch() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();

        // Update the functions using partial update
        Functions partialUpdatedFunctions = new Functions();
        partialUpdatedFunctions.setId(functions.getId());

        partialUpdatedFunctions.name(UPDATED_NAME).code(UPDATED_CODE);

        restFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunctions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunctions))
            )
            .andExpect(status().isOk());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
        Functions testFunctions = functionsList.get(functionsList.size() - 1);
        assertThat(testFunctions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFunctions.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingFunctions() throws Exception {
        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();
        functions.setId(longCount.incrementAndGet());

        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, functionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunctions() throws Exception {
        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();
        functions.setId(longCount.incrementAndGet());

        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(functionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunctions() throws Exception {
        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();
        functions.setId(longCount.incrementAndGet());

        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(functionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        int databaseSizeBeforeDelete = functionsRepository.findAll().size();

        // Delete the functions
        restFunctionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, functions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
