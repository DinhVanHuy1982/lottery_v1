package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.RandomResults;
import com.lottery.app.repository.RandomResultsRepository;
import com.lottery.app.service.dto.RandomResultsDTO;
import com.lottery.app.service.mapper.RandomResultsMapper;
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
 * Integration tests for the {@link RandomResultsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RandomResultsResourceIT {

    private static final Instant DEFAULT_RANDOM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RANDOM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PRIZE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRIZE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final Long DEFAULT_RANDOM_USER_PLAY = 1L;
    private static final Long UPDATED_RANDOM_USER_PLAY = 2L;

    private static final Long DEFAULT_USER_PLAY = 1L;
    private static final Long UPDATED_USER_PLAY = 2L;

    private static final Long DEFAULT_RANDOM_USER_SUCCESS = 1L;
    private static final Long UPDATED_RANDOM_USER_SUCCESS = 2L;

    private static final Long DEFAULT_USER_SUCCESS = 1L;
    private static final Long UPDATED_USER_SUCCESS = 2L;

    private static final String ENTITY_API_URL = "/api/random-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RandomResultsRepository randomResultsRepository;

    @Autowired
    private RandomResultsMapper randomResultsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRandomResultsMockMvc;

    private RandomResults randomResults;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RandomResults createEntity(EntityManager em) {
        RandomResults randomResults = new RandomResults()
            .randomDate(DEFAULT_RANDOM_DATE)
            .prizeCode(DEFAULT_PRIZE_CODE)
            .result(DEFAULT_RESULT)
            .randomUserPlay(DEFAULT_RANDOM_USER_PLAY)
            .userPlay(DEFAULT_USER_PLAY)
            .randomUserSuccess(DEFAULT_RANDOM_USER_SUCCESS)
            .userSuccess(DEFAULT_USER_SUCCESS);
        return randomResults;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RandomResults createUpdatedEntity(EntityManager em) {
        RandomResults randomResults = new RandomResults()
            .randomDate(UPDATED_RANDOM_DATE)
            .prizeCode(UPDATED_PRIZE_CODE)
            .result(UPDATED_RESULT)
            .randomUserPlay(UPDATED_RANDOM_USER_PLAY)
            .userPlay(UPDATED_USER_PLAY)
            .randomUserSuccess(UPDATED_RANDOM_USER_SUCCESS)
            .userSuccess(UPDATED_USER_SUCCESS);
        return randomResults;
    }

    @BeforeEach
    public void initTest() {
        randomResults = createEntity(em);
    }

    @Test
    @Transactional
    void createRandomResults() throws Exception {
        int databaseSizeBeforeCreate = randomResultsRepository.findAll().size();
        // Create the RandomResults
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);
        restRandomResultsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeCreate + 1);
        RandomResults testRandomResults = randomResultsList.get(randomResultsList.size() - 1);
        assertThat(testRandomResults.getRandomDate()).isEqualTo(DEFAULT_RANDOM_DATE);
        assertThat(testRandomResults.getPrizeCode()).isEqualTo(DEFAULT_PRIZE_CODE);
        assertThat(testRandomResults.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testRandomResults.getRandomUserPlay()).isEqualTo(DEFAULT_RANDOM_USER_PLAY);
        assertThat(testRandomResults.getUserPlay()).isEqualTo(DEFAULT_USER_PLAY);
        assertThat(testRandomResults.getRandomUserSuccess()).isEqualTo(DEFAULT_RANDOM_USER_SUCCESS);
        assertThat(testRandomResults.getUserSuccess()).isEqualTo(DEFAULT_USER_SUCCESS);
    }

    @Test
    @Transactional
    void createRandomResultsWithExistingId() throws Exception {
        // Create the RandomResults with an existing ID
        randomResults.setId(1L);
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);

        int databaseSizeBeforeCreate = randomResultsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRandomResultsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRandomResults() throws Exception {
        // Initialize the database
        randomResultsRepository.saveAndFlush(randomResults);

        // Get all the randomResultsList
        restRandomResultsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(randomResults.getId().intValue())))
            .andExpect(jsonPath("$.[*].randomDate").value(hasItem(DEFAULT_RANDOM_DATE.toString())))
            .andExpect(jsonPath("$.[*].prizeCode").value(hasItem(DEFAULT_PRIZE_CODE)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].randomUserPlay").value(hasItem(DEFAULT_RANDOM_USER_PLAY.intValue())))
            .andExpect(jsonPath("$.[*].userPlay").value(hasItem(DEFAULT_USER_PLAY.intValue())))
            .andExpect(jsonPath("$.[*].randomUserSuccess").value(hasItem(DEFAULT_RANDOM_USER_SUCCESS.intValue())))
            .andExpect(jsonPath("$.[*].userSuccess").value(hasItem(DEFAULT_USER_SUCCESS.intValue())));
    }

    @Test
    @Transactional
    void getRandomResults() throws Exception {
        // Initialize the database
        randomResultsRepository.saveAndFlush(randomResults);

        // Get the randomResults
        restRandomResultsMockMvc
            .perform(get(ENTITY_API_URL_ID, randomResults.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(randomResults.getId().intValue()))
            .andExpect(jsonPath("$.randomDate").value(DEFAULT_RANDOM_DATE.toString()))
            .andExpect(jsonPath("$.prizeCode").value(DEFAULT_PRIZE_CODE))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.randomUserPlay").value(DEFAULT_RANDOM_USER_PLAY.intValue()))
            .andExpect(jsonPath("$.userPlay").value(DEFAULT_USER_PLAY.intValue()))
            .andExpect(jsonPath("$.randomUserSuccess").value(DEFAULT_RANDOM_USER_SUCCESS.intValue()))
            .andExpect(jsonPath("$.userSuccess").value(DEFAULT_USER_SUCCESS.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRandomResults() throws Exception {
        // Get the randomResults
        restRandomResultsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRandomResults() throws Exception {
        // Initialize the database
        randomResultsRepository.saveAndFlush(randomResults);

        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();

        // Update the randomResults
        RandomResults updatedRandomResults = randomResultsRepository.findById(randomResults.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRandomResults are not directly saved in db
        em.detach(updatedRandomResults);
        updatedRandomResults
            .randomDate(UPDATED_RANDOM_DATE)
            .prizeCode(UPDATED_PRIZE_CODE)
            .result(UPDATED_RESULT)
            .randomUserPlay(UPDATED_RANDOM_USER_PLAY)
            .userPlay(UPDATED_USER_PLAY)
            .randomUserSuccess(UPDATED_RANDOM_USER_SUCCESS)
            .userSuccess(UPDATED_USER_SUCCESS);
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(updatedRandomResults);

        restRandomResultsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, randomResultsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isOk());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
        RandomResults testRandomResults = randomResultsList.get(randomResultsList.size() - 1);
        assertThat(testRandomResults.getRandomDate()).isEqualTo(UPDATED_RANDOM_DATE);
        assertThat(testRandomResults.getPrizeCode()).isEqualTo(UPDATED_PRIZE_CODE);
        assertThat(testRandomResults.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testRandomResults.getRandomUserPlay()).isEqualTo(UPDATED_RANDOM_USER_PLAY);
        assertThat(testRandomResults.getUserPlay()).isEqualTo(UPDATED_USER_PLAY);
        assertThat(testRandomResults.getRandomUserSuccess()).isEqualTo(UPDATED_RANDOM_USER_SUCCESS);
        assertThat(testRandomResults.getUserSuccess()).isEqualTo(UPDATED_USER_SUCCESS);
    }

    @Test
    @Transactional
    void putNonExistingRandomResults() throws Exception {
        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();
        randomResults.setId(longCount.incrementAndGet());

        // Create the RandomResults
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRandomResultsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, randomResultsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRandomResults() throws Exception {
        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();
        randomResults.setId(longCount.incrementAndGet());

        // Create the RandomResults
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRandomResultsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRandomResults() throws Exception {
        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();
        randomResults.setId(longCount.incrementAndGet());

        // Create the RandomResults
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRandomResultsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRandomResultsWithPatch() throws Exception {
        // Initialize the database
        randomResultsRepository.saveAndFlush(randomResults);

        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();

        // Update the randomResults using partial update
        RandomResults partialUpdatedRandomResults = new RandomResults();
        partialUpdatedRandomResults.setId(randomResults.getId());

        partialUpdatedRandomResults.result(UPDATED_RESULT).randomUserPlay(UPDATED_RANDOM_USER_PLAY).userPlay(UPDATED_USER_PLAY);

        restRandomResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRandomResults.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRandomResults))
            )
            .andExpect(status().isOk());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
        RandomResults testRandomResults = randomResultsList.get(randomResultsList.size() - 1);
        assertThat(testRandomResults.getRandomDate()).isEqualTo(DEFAULT_RANDOM_DATE);
        assertThat(testRandomResults.getPrizeCode()).isEqualTo(DEFAULT_PRIZE_CODE);
        assertThat(testRandomResults.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testRandomResults.getRandomUserPlay()).isEqualTo(UPDATED_RANDOM_USER_PLAY);
        assertThat(testRandomResults.getUserPlay()).isEqualTo(UPDATED_USER_PLAY);
        assertThat(testRandomResults.getRandomUserSuccess()).isEqualTo(DEFAULT_RANDOM_USER_SUCCESS);
        assertThat(testRandomResults.getUserSuccess()).isEqualTo(DEFAULT_USER_SUCCESS);
    }

    @Test
    @Transactional
    void fullUpdateRandomResultsWithPatch() throws Exception {
        // Initialize the database
        randomResultsRepository.saveAndFlush(randomResults);

        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();

        // Update the randomResults using partial update
        RandomResults partialUpdatedRandomResults = new RandomResults();
        partialUpdatedRandomResults.setId(randomResults.getId());

        partialUpdatedRandomResults
            .randomDate(UPDATED_RANDOM_DATE)
            .prizeCode(UPDATED_PRIZE_CODE)
            .result(UPDATED_RESULT)
            .randomUserPlay(UPDATED_RANDOM_USER_PLAY)
            .userPlay(UPDATED_USER_PLAY)
            .randomUserSuccess(UPDATED_RANDOM_USER_SUCCESS)
            .userSuccess(UPDATED_USER_SUCCESS);

        restRandomResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRandomResults.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRandomResults))
            )
            .andExpect(status().isOk());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
        RandomResults testRandomResults = randomResultsList.get(randomResultsList.size() - 1);
        assertThat(testRandomResults.getRandomDate()).isEqualTo(UPDATED_RANDOM_DATE);
        assertThat(testRandomResults.getPrizeCode()).isEqualTo(UPDATED_PRIZE_CODE);
        assertThat(testRandomResults.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testRandomResults.getRandomUserPlay()).isEqualTo(UPDATED_RANDOM_USER_PLAY);
        assertThat(testRandomResults.getUserPlay()).isEqualTo(UPDATED_USER_PLAY);
        assertThat(testRandomResults.getRandomUserSuccess()).isEqualTo(UPDATED_RANDOM_USER_SUCCESS);
        assertThat(testRandomResults.getUserSuccess()).isEqualTo(UPDATED_USER_SUCCESS);
    }

    @Test
    @Transactional
    void patchNonExistingRandomResults() throws Exception {
        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();
        randomResults.setId(longCount.incrementAndGet());

        // Create the RandomResults
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRandomResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, randomResultsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRandomResults() throws Exception {
        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();
        randomResults.setId(longCount.incrementAndGet());

        // Create the RandomResults
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRandomResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRandomResults() throws Exception {
        int databaseSizeBeforeUpdate = randomResultsRepository.findAll().size();
        randomResults.setId(longCount.incrementAndGet());

        // Create the RandomResults
        RandomResultsDTO randomResultsDTO = randomResultsMapper.toDto(randomResults);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRandomResultsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(randomResultsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RandomResults in the database
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRandomResults() throws Exception {
        // Initialize the database
        randomResultsRepository.saveAndFlush(randomResults);

        int databaseSizeBeforeDelete = randomResultsRepository.findAll().size();

        // Delete the randomResults
        restRandomResultsMockMvc
            .perform(delete(ENTITY_API_URL_ID, randomResults.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RandomResults> randomResultsList = randomResultsRepository.findAll();
        assertThat(randomResultsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
