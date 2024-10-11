package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.LevelDepositsResult;
import com.lottery.app.repository.LevelDepositsResultRepository;
import com.lottery.app.service.dto.LevelDepositsResultDTO;
import com.lottery.app.service.mapper.LevelDepositsResultMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link LevelDepositsResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LevelDepositsResultResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL_DEPOSITS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL_DEPOSITS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RANDOM_RESULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RANDOM_RESULT_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESULT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/level-deposits-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelDepositsResultRepository levelDepositsResultRepository;

    @Autowired
    private LevelDepositsResultMapper levelDepositsResultMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelDepositsResultMockMvc;

    private LevelDepositsResult levelDepositsResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelDepositsResult createEntity(EntityManager em) {
        LevelDepositsResult levelDepositsResult = new LevelDepositsResult()
            .code(DEFAULT_CODE)
            .levelDepositsCode(DEFAULT_LEVEL_DEPOSITS_CODE)
            .randomResultCode(DEFAULT_RANDOM_RESULT_CODE)
            .resultDate(DEFAULT_RESULT_DATE);
        return levelDepositsResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelDepositsResult createUpdatedEntity(EntityManager em) {
        LevelDepositsResult levelDepositsResult = new LevelDepositsResult()
            .code(UPDATED_CODE)
            .levelDepositsCode(UPDATED_LEVEL_DEPOSITS_CODE)
            .randomResultCode(UPDATED_RANDOM_RESULT_CODE)
            .resultDate(UPDATED_RESULT_DATE);
        return levelDepositsResult;
    }

    @BeforeEach
    public void initTest() {
        levelDepositsResult = createEntity(em);
    }

    @Test
    @Transactional
    void createLevelDepositsResult() throws Exception {
        int databaseSizeBeforeCreate = levelDepositsResultRepository.findAll().size();
        // Create the LevelDepositsResult
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);
        restLevelDepositsResultMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeCreate + 1);
        LevelDepositsResult testLevelDepositsResult = levelDepositsResultList.get(levelDepositsResultList.size() - 1);
        assertThat(testLevelDepositsResult.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLevelDepositsResult.getLevelDepositsCode()).isEqualTo(DEFAULT_LEVEL_DEPOSITS_CODE);
        assertThat(testLevelDepositsResult.getRandomResultCode()).isEqualTo(DEFAULT_RANDOM_RESULT_CODE);
        assertThat(testLevelDepositsResult.getResultDate()).isEqualTo(DEFAULT_RESULT_DATE);
    }

    @Test
    @Transactional
    void createLevelDepositsResultWithExistingId() throws Exception {
        // Create the LevelDepositsResult with an existing ID
        levelDepositsResult.setId(1L);
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);

        int databaseSizeBeforeCreate = levelDepositsResultRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelDepositsResultMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLevelDepositsResults() throws Exception {
        // Initialize the database
        levelDepositsResultRepository.saveAndFlush(levelDepositsResult);

        // Get all the levelDepositsResultList
        restLevelDepositsResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelDepositsResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].levelDepositsCode").value(hasItem(DEFAULT_LEVEL_DEPOSITS_CODE)))
            .andExpect(jsonPath("$.[*].randomResultCode").value(hasItem(DEFAULT_RANDOM_RESULT_CODE)))
            .andExpect(jsonPath("$.[*].resultDate").value(hasItem(DEFAULT_RESULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getLevelDepositsResult() throws Exception {
        // Initialize the database
        levelDepositsResultRepository.saveAndFlush(levelDepositsResult);

        // Get the levelDepositsResult
        restLevelDepositsResultMockMvc
            .perform(get(ENTITY_API_URL_ID, levelDepositsResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelDepositsResult.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.levelDepositsCode").value(DEFAULT_LEVEL_DEPOSITS_CODE))
            .andExpect(jsonPath("$.randomResultCode").value(DEFAULT_RANDOM_RESULT_CODE))
            .andExpect(jsonPath("$.resultDate").value(DEFAULT_RESULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLevelDepositsResult() throws Exception {
        // Get the levelDepositsResult
        restLevelDepositsResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevelDepositsResult() throws Exception {
        // Initialize the database
        levelDepositsResultRepository.saveAndFlush(levelDepositsResult);

        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();

        // Update the levelDepositsResult
        LevelDepositsResult updatedLevelDepositsResult = levelDepositsResultRepository.findById(levelDepositsResult.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLevelDepositsResult are not directly saved in db
        em.detach(updatedLevelDepositsResult);
        updatedLevelDepositsResult
            .code(UPDATED_CODE)
            .levelDepositsCode(UPDATED_LEVEL_DEPOSITS_CODE)
            .randomResultCode(UPDATED_RANDOM_RESULT_CODE)
            .resultDate(UPDATED_RESULT_DATE);
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(updatedLevelDepositsResult);

        restLevelDepositsResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelDepositsResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isOk());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
        LevelDepositsResult testLevelDepositsResult = levelDepositsResultList.get(levelDepositsResultList.size() - 1);
        assertThat(testLevelDepositsResult.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLevelDepositsResult.getLevelDepositsCode()).isEqualTo(UPDATED_LEVEL_DEPOSITS_CODE);
        assertThat(testLevelDepositsResult.getRandomResultCode()).isEqualTo(UPDATED_RANDOM_RESULT_CODE);
        assertThat(testLevelDepositsResult.getResultDate()).isEqualTo(UPDATED_RESULT_DATE);
    }

    @Test
    @Transactional
    void putNonExistingLevelDepositsResult() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();
        levelDepositsResult.setId(longCount.incrementAndGet());

        // Create the LevelDepositsResult
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelDepositsResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelDepositsResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevelDepositsResult() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();
        levelDepositsResult.setId(longCount.incrementAndGet());

        // Create the LevelDepositsResult
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevelDepositsResult() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();
        levelDepositsResult.setId(longCount.incrementAndGet());

        // Create the LevelDepositsResult
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsResultMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLevelDepositsResultWithPatch() throws Exception {
        // Initialize the database
        levelDepositsResultRepository.saveAndFlush(levelDepositsResult);

        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();

        // Update the levelDepositsResult using partial update
        LevelDepositsResult partialUpdatedLevelDepositsResult = new LevelDepositsResult();
        partialUpdatedLevelDepositsResult.setId(levelDepositsResult.getId());

        partialUpdatedLevelDepositsResult.code(UPDATED_CODE).levelDepositsCode(UPDATED_LEVEL_DEPOSITS_CODE);

        restLevelDepositsResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelDepositsResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelDepositsResult))
            )
            .andExpect(status().isOk());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
        LevelDepositsResult testLevelDepositsResult = levelDepositsResultList.get(levelDepositsResultList.size() - 1);
        assertThat(testLevelDepositsResult.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLevelDepositsResult.getLevelDepositsCode()).isEqualTo(UPDATED_LEVEL_DEPOSITS_CODE);
        assertThat(testLevelDepositsResult.getRandomResultCode()).isEqualTo(DEFAULT_RANDOM_RESULT_CODE);
        assertThat(testLevelDepositsResult.getResultDate()).isEqualTo(DEFAULT_RESULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateLevelDepositsResultWithPatch() throws Exception {
        // Initialize the database
        levelDepositsResultRepository.saveAndFlush(levelDepositsResult);

        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();

        // Update the levelDepositsResult using partial update
        LevelDepositsResult partialUpdatedLevelDepositsResult = new LevelDepositsResult();
        partialUpdatedLevelDepositsResult.setId(levelDepositsResult.getId());

        partialUpdatedLevelDepositsResult
            .code(UPDATED_CODE)
            .levelDepositsCode(UPDATED_LEVEL_DEPOSITS_CODE)
            .randomResultCode(UPDATED_RANDOM_RESULT_CODE)
            .resultDate(UPDATED_RESULT_DATE);

        restLevelDepositsResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelDepositsResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelDepositsResult))
            )
            .andExpect(status().isOk());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
        LevelDepositsResult testLevelDepositsResult = levelDepositsResultList.get(levelDepositsResultList.size() - 1);
        assertThat(testLevelDepositsResult.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLevelDepositsResult.getLevelDepositsCode()).isEqualTo(UPDATED_LEVEL_DEPOSITS_CODE);
        assertThat(testLevelDepositsResult.getRandomResultCode()).isEqualTo(UPDATED_RANDOM_RESULT_CODE);
        assertThat(testLevelDepositsResult.getResultDate()).isEqualTo(UPDATED_RESULT_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingLevelDepositsResult() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();
        levelDepositsResult.setId(longCount.incrementAndGet());

        // Create the LevelDepositsResult
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelDepositsResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelDepositsResultDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevelDepositsResult() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();
        levelDepositsResult.setId(longCount.incrementAndGet());

        // Create the LevelDepositsResult
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevelDepositsResult() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsResultRepository.findAll().size();
        levelDepositsResult.setId(longCount.incrementAndGet());

        // Create the LevelDepositsResult
        LevelDepositsResultDTO levelDepositsResultDTO = levelDepositsResultMapper.toDto(levelDepositsResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsResultMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelDepositsResult in the database
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevelDepositsResult() throws Exception {
        // Initialize the database
        levelDepositsResultRepository.saveAndFlush(levelDepositsResult);

        int databaseSizeBeforeDelete = levelDepositsResultRepository.findAll().size();

        // Delete the levelDepositsResult
        restLevelDepositsResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, levelDepositsResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelDepositsResult> levelDepositsResultList = levelDepositsResultRepository.findAll();
        assertThat(levelDepositsResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
