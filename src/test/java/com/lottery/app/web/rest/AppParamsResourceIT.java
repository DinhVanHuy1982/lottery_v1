package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.AppParams;
import com.lottery.app.repository.AppParamsRepository;
import com.lottery.app.service.dto.AppParamsDTO;
import com.lottery.app.service.mapper.AppParamsMapper;
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
 * Integration tests for the {@link AppParamsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppParamsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-params";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppParamsRepository appParamsRepository;

    @Autowired
    private AppParamsMapper appParamsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppParamsMockMvc;

    private AppParams appParams;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppParams createEntity(EntityManager em) {
        AppParams appParams = new AppParams().code(DEFAULT_CODE).value(DEFAULT_VALUE).type(DEFAULT_TYPE);
        return appParams;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppParams createUpdatedEntity(EntityManager em) {
        AppParams appParams = new AppParams().code(UPDATED_CODE).value(UPDATED_VALUE).type(UPDATED_TYPE);
        return appParams;
    }

    @BeforeEach
    public void initTest() {
        appParams = createEntity(em);
    }

    @Test
    @Transactional
    void createAppParams() throws Exception {
        int databaseSizeBeforeCreate = appParamsRepository.findAll().size();
        // Create the AppParams
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);
        restAppParamsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appParamsDTO)))
            .andExpect(status().isCreated());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeCreate + 1);
        AppParams testAppParams = appParamsList.get(appParamsList.size() - 1);
        assertThat(testAppParams.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAppParams.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAppParams.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createAppParamsWithExistingId() throws Exception {
        // Create the AppParams with an existing ID
        appParams.setId(1L);
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        int databaseSizeBeforeCreate = appParamsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppParamsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appParamsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appParamsRepository.findAll().size();
        // set the field null
        appParams.setCode(null);

        // Create the AppParams, which fails.
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        restAppParamsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appParamsDTO)))
            .andExpect(status().isBadRequest());

        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAppParams() throws Exception {
        // Initialize the database
        appParamsRepository.saveAndFlush(appParams);

        // Get all the appParamsList
        restAppParamsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appParams.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getAppParams() throws Exception {
        // Initialize the database
        appParamsRepository.saveAndFlush(appParams);

        // Get the appParams
        restAppParamsMockMvc
            .perform(get(ENTITY_API_URL_ID, appParams.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appParams.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingAppParams() throws Exception {
        // Get the appParams
        restAppParamsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppParams() throws Exception {
        // Initialize the database
        appParamsRepository.saveAndFlush(appParams);

        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();

        // Update the appParams
        AppParams updatedAppParams = appParamsRepository.findById(appParams.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppParams are not directly saved in db
        em.detach(updatedAppParams);
        updatedAppParams.code(UPDATED_CODE).value(UPDATED_VALUE).type(UPDATED_TYPE);
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(updatedAppParams);

        restAppParamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appParamsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appParamsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
        AppParams testAppParams = appParamsList.get(appParamsList.size() - 1);
        assertThat(testAppParams.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAppParams.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAppParams.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAppParams() throws Exception {
        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();
        appParams.setId(longCount.incrementAndGet());

        // Create the AppParams
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppParamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appParamsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appParamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppParams() throws Exception {
        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();
        appParams.setId(longCount.incrementAndGet());

        // Create the AppParams
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppParamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appParamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppParams() throws Exception {
        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();
        appParams.setId(longCount.incrementAndGet());

        // Create the AppParams
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppParamsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appParamsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppParamsWithPatch() throws Exception {
        // Initialize the database
        appParamsRepository.saveAndFlush(appParams);

        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();

        // Update the appParams using partial update
        AppParams partialUpdatedAppParams = new AppParams();
        partialUpdatedAppParams.setId(appParams.getId());

        partialUpdatedAppParams.type(UPDATED_TYPE);

        restAppParamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppParams.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppParams))
            )
            .andExpect(status().isOk());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
        AppParams testAppParams = appParamsList.get(appParamsList.size() - 1);
        assertThat(testAppParams.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAppParams.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAppParams.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAppParamsWithPatch() throws Exception {
        // Initialize the database
        appParamsRepository.saveAndFlush(appParams);

        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();

        // Update the appParams using partial update
        AppParams partialUpdatedAppParams = new AppParams();
        partialUpdatedAppParams.setId(appParams.getId());

        partialUpdatedAppParams.code(UPDATED_CODE).value(UPDATED_VALUE).type(UPDATED_TYPE);

        restAppParamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppParams.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppParams))
            )
            .andExpect(status().isOk());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
        AppParams testAppParams = appParamsList.get(appParamsList.size() - 1);
        assertThat(testAppParams.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAppParams.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAppParams.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAppParams() throws Exception {
        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();
        appParams.setId(longCount.incrementAndGet());

        // Create the AppParams
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppParamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appParamsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appParamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppParams() throws Exception {
        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();
        appParams.setId(longCount.incrementAndGet());

        // Create the AppParams
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppParamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appParamsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppParams() throws Exception {
        int databaseSizeBeforeUpdate = appParamsRepository.findAll().size();
        appParams.setId(longCount.incrementAndGet());

        // Create the AppParams
        AppParamsDTO appParamsDTO = appParamsMapper.toDto(appParams);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppParamsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appParamsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppParams in the database
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppParams() throws Exception {
        // Initialize the database
        appParamsRepository.saveAndFlush(appParams);

        int databaseSizeBeforeDelete = appParamsRepository.findAll().size();

        // Delete the appParams
        restAppParamsMockMvc
            .perform(delete(ENTITY_API_URL_ID, appParams.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppParams> appParamsList = appParamsRepository.findAll();
        assertThat(appParamsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
