package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.RoleFunctions;
import com.lottery.app.repository.RoleFunctionsRepository;
import com.lottery.app.service.dto.RoleFunctionsDTO;
import com.lottery.app.service.mapper.RoleFunctionsMapper;
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
 * Integration tests for the {@link RoleFunctionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleFunctionsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/role-functions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleFunctionsRepository roleFunctionsRepository;

    @Autowired
    private RoleFunctionsMapper roleFunctionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleFunctionsMockMvc;

    private RoleFunctions roleFunctions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleFunctions createEntity(EntityManager em) {
        RoleFunctions roleFunctions = new RoleFunctions()
            .code(DEFAULT_CODE)
            .roleCode(DEFAULT_ROLE_CODE)
            .functionCode(DEFAULT_FUNCTION_CODE);
        return roleFunctions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleFunctions createUpdatedEntity(EntityManager em) {
        RoleFunctions roleFunctions = new RoleFunctions()
            .code(UPDATED_CODE)
            .roleCode(UPDATED_ROLE_CODE)
            .functionCode(UPDATED_FUNCTION_CODE);
        return roleFunctions;
    }

    @BeforeEach
    public void initTest() {
        roleFunctions = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleFunctions() throws Exception {
        int databaseSizeBeforeCreate = roleFunctionsRepository.findAll().size();
        // Create the RoleFunctions
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);
        restRoleFunctionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeCreate + 1);
        RoleFunctions testRoleFunctions = roleFunctionsList.get(roleFunctionsList.size() - 1);
        assertThat(testRoleFunctions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRoleFunctions.getRoleCode()).isEqualTo(DEFAULT_ROLE_CODE);
        assertThat(testRoleFunctions.getFunctionCode()).isEqualTo(DEFAULT_FUNCTION_CODE);
    }

    @Test
    @Transactional
    void createRoleFunctionsWithExistingId() throws Exception {
        // Create the RoleFunctions with an existing ID
        roleFunctions.setId(1L);
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);

        int databaseSizeBeforeCreate = roleFunctionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleFunctionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoleFunctions() throws Exception {
        // Initialize the database
        roleFunctionsRepository.saveAndFlush(roleFunctions);

        // Get all the roleFunctionsList
        restRoleFunctionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleFunctions.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].roleCode").value(hasItem(DEFAULT_ROLE_CODE)))
            .andExpect(jsonPath("$.[*].functionCode").value(hasItem(DEFAULT_FUNCTION_CODE)));
    }

    @Test
    @Transactional
    void getRoleFunctions() throws Exception {
        // Initialize the database
        roleFunctionsRepository.saveAndFlush(roleFunctions);

        // Get the roleFunctions
        restRoleFunctionsMockMvc
            .perform(get(ENTITY_API_URL_ID, roleFunctions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleFunctions.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.roleCode").value(DEFAULT_ROLE_CODE))
            .andExpect(jsonPath("$.functionCode").value(DEFAULT_FUNCTION_CODE));
    }

    @Test
    @Transactional
    void getNonExistingRoleFunctions() throws Exception {
        // Get the roleFunctions
        restRoleFunctionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoleFunctions() throws Exception {
        // Initialize the database
        roleFunctionsRepository.saveAndFlush(roleFunctions);

        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();

        // Update the roleFunctions
        RoleFunctions updatedRoleFunctions = roleFunctionsRepository.findById(roleFunctions.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRoleFunctions are not directly saved in db
        em.detach(updatedRoleFunctions);
        updatedRoleFunctions.code(UPDATED_CODE).roleCode(UPDATED_ROLE_CODE).functionCode(UPDATED_FUNCTION_CODE);
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(updatedRoleFunctions);

        restRoleFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleFunctionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
        RoleFunctions testRoleFunctions = roleFunctionsList.get(roleFunctionsList.size() - 1);
        assertThat(testRoleFunctions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoleFunctions.getRoleCode()).isEqualTo(UPDATED_ROLE_CODE);
        assertThat(testRoleFunctions.getFunctionCode()).isEqualTo(UPDATED_FUNCTION_CODE);
    }

    @Test
    @Transactional
    void putNonExistingRoleFunctions() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();
        roleFunctions.setId(longCount.incrementAndGet());

        // Create the RoleFunctions
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleFunctionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleFunctions() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();
        roleFunctions.setId(longCount.incrementAndGet());

        // Create the RoleFunctions
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleFunctions() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();
        roleFunctions.setId(longCount.incrementAndGet());

        // Create the RoleFunctions
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleFunctionsWithPatch() throws Exception {
        // Initialize the database
        roleFunctionsRepository.saveAndFlush(roleFunctions);

        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();

        // Update the roleFunctions using partial update
        RoleFunctions partialUpdatedRoleFunctions = new RoleFunctions();
        partialUpdatedRoleFunctions.setId(roleFunctions.getId());

        partialUpdatedRoleFunctions.roleCode(UPDATED_ROLE_CODE).functionCode(UPDATED_FUNCTION_CODE);

        restRoleFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleFunctions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleFunctions))
            )
            .andExpect(status().isOk());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
        RoleFunctions testRoleFunctions = roleFunctionsList.get(roleFunctionsList.size() - 1);
        assertThat(testRoleFunctions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRoleFunctions.getRoleCode()).isEqualTo(UPDATED_ROLE_CODE);
        assertThat(testRoleFunctions.getFunctionCode()).isEqualTo(UPDATED_FUNCTION_CODE);
    }

    @Test
    @Transactional
    void fullUpdateRoleFunctionsWithPatch() throws Exception {
        // Initialize the database
        roleFunctionsRepository.saveAndFlush(roleFunctions);

        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();

        // Update the roleFunctions using partial update
        RoleFunctions partialUpdatedRoleFunctions = new RoleFunctions();
        partialUpdatedRoleFunctions.setId(roleFunctions.getId());

        partialUpdatedRoleFunctions.code(UPDATED_CODE).roleCode(UPDATED_ROLE_CODE).functionCode(UPDATED_FUNCTION_CODE);

        restRoleFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleFunctions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleFunctions))
            )
            .andExpect(status().isOk());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
        RoleFunctions testRoleFunctions = roleFunctionsList.get(roleFunctionsList.size() - 1);
        assertThat(testRoleFunctions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoleFunctions.getRoleCode()).isEqualTo(UPDATED_ROLE_CODE);
        assertThat(testRoleFunctions.getFunctionCode()).isEqualTo(UPDATED_FUNCTION_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingRoleFunctions() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();
        roleFunctions.setId(longCount.incrementAndGet());

        // Create the RoleFunctions
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleFunctionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleFunctions() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();
        roleFunctions.setId(longCount.incrementAndGet());

        // Create the RoleFunctions
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleFunctions() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionsRepository.findAll().size();
        roleFunctions.setId(longCount.incrementAndGet());

        // Create the RoleFunctions
        RoleFunctionsDTO roleFunctionsDTO = roleFunctionsMapper.toDto(roleFunctions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleFunctions in the database
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleFunctions() throws Exception {
        // Initialize the database
        roleFunctionsRepository.saveAndFlush(roleFunctions);

        int databaseSizeBeforeDelete = roleFunctionsRepository.findAll().size();

        // Delete the roleFunctions
        restRoleFunctionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleFunctions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleFunctions> roleFunctionsList = roleFunctionsRepository.findAll();
        assertThat(roleFunctionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
