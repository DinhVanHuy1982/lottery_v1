package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.ResultsEveryDay;
import com.lottery.app.repository.ResultsEveryDayRepository;
import com.lottery.app.service.dto.ResultsEveryDayDTO;
import com.lottery.app.service.mapper.ResultsEveryDayMapper;
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
 * Integration tests for the {@link ResultsEveryDayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultsEveryDayResourceIT {

    private static final Instant DEFAULT_RESULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESULT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PRIZE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRIZE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/results-every-days";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResultsEveryDayRepository resultsEveryDayRepository;

    @Autowired
    private ResultsEveryDayMapper resultsEveryDayMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultsEveryDayMockMvc;

    private ResultsEveryDay resultsEveryDay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultsEveryDay createEntity(EntityManager em) {
        ResultsEveryDay resultsEveryDay = new ResultsEveryDay()
            .resultDate(DEFAULT_RESULT_DATE)
            .prizeCode(DEFAULT_PRIZE_CODE)
            .result(DEFAULT_RESULT);
        return resultsEveryDay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultsEveryDay createUpdatedEntity(EntityManager em) {
        ResultsEveryDay resultsEveryDay = new ResultsEveryDay()
            .resultDate(UPDATED_RESULT_DATE)
            .prizeCode(UPDATED_PRIZE_CODE)
            .result(UPDATED_RESULT);
        return resultsEveryDay;
    }

    @BeforeEach
    public void initTest() {
        resultsEveryDay = createEntity(em);
    }

    @Test
    @Transactional
    void createResultsEveryDay() throws Exception {
        int databaseSizeBeforeCreate = resultsEveryDayRepository.findAll().size();
        // Create the ResultsEveryDay
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);
        restResultsEveryDayMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeCreate + 1);
        ResultsEveryDay testResultsEveryDay = resultsEveryDayList.get(resultsEveryDayList.size() - 1);
        assertThat(testResultsEveryDay.getResultDate()).isEqualTo(DEFAULT_RESULT_DATE);
        assertThat(testResultsEveryDay.getPrizeCode()).isEqualTo(DEFAULT_PRIZE_CODE);
        assertThat(testResultsEveryDay.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    void createResultsEveryDayWithExistingId() throws Exception {
        // Create the ResultsEveryDay with an existing ID
        resultsEveryDay.setId(1L);
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);

        int databaseSizeBeforeCreate = resultsEveryDayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultsEveryDayMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResultsEveryDays() throws Exception {
        // Initialize the database
        resultsEveryDayRepository.saveAndFlush(resultsEveryDay);

        // Get all the resultsEveryDayList
        restResultsEveryDayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultsEveryDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].resultDate").value(hasItem(DEFAULT_RESULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].prizeCode").value(hasItem(DEFAULT_PRIZE_CODE)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }

    @Test
    @Transactional
    void getResultsEveryDay() throws Exception {
        // Initialize the database
        resultsEveryDayRepository.saveAndFlush(resultsEveryDay);

        // Get the resultsEveryDay
        restResultsEveryDayMockMvc
            .perform(get(ENTITY_API_URL_ID, resultsEveryDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultsEveryDay.getId().intValue()))
            .andExpect(jsonPath("$.resultDate").value(DEFAULT_RESULT_DATE.toString()))
            .andExpect(jsonPath("$.prizeCode").value(DEFAULT_PRIZE_CODE))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT));
    }

    @Test
    @Transactional
    void getNonExistingResultsEveryDay() throws Exception {
        // Get the resultsEveryDay
        restResultsEveryDayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResultsEveryDay() throws Exception {
        // Initialize the database
        resultsEveryDayRepository.saveAndFlush(resultsEveryDay);

        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();

        // Update the resultsEveryDay
        ResultsEveryDay updatedResultsEveryDay = resultsEveryDayRepository.findById(resultsEveryDay.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedResultsEveryDay are not directly saved in db
        em.detach(updatedResultsEveryDay);
        updatedResultsEveryDay.resultDate(UPDATED_RESULT_DATE).prizeCode(UPDATED_PRIZE_CODE).result(UPDATED_RESULT);
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(updatedResultsEveryDay);

        restResultsEveryDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultsEveryDayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
        ResultsEveryDay testResultsEveryDay = resultsEveryDayList.get(resultsEveryDayList.size() - 1);
        assertThat(testResultsEveryDay.getResultDate()).isEqualTo(UPDATED_RESULT_DATE);
        assertThat(testResultsEveryDay.getPrizeCode()).isEqualTo(UPDATED_PRIZE_CODE);
        assertThat(testResultsEveryDay.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void putNonExistingResultsEveryDay() throws Exception {
        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();
        resultsEveryDay.setId(longCount.incrementAndGet());

        // Create the ResultsEveryDay
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultsEveryDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resultsEveryDayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResultsEveryDay() throws Exception {
        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();
        resultsEveryDay.setId(longCount.incrementAndGet());

        // Create the ResultsEveryDay
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultsEveryDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResultsEveryDay() throws Exception {
        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();
        resultsEveryDay.setId(longCount.incrementAndGet());

        // Create the ResultsEveryDay
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultsEveryDayMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResultsEveryDayWithPatch() throws Exception {
        // Initialize the database
        resultsEveryDayRepository.saveAndFlush(resultsEveryDay);

        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();

        // Update the resultsEveryDay using partial update
        ResultsEveryDay partialUpdatedResultsEveryDay = new ResultsEveryDay();
        partialUpdatedResultsEveryDay.setId(resultsEveryDay.getId());

        restResultsEveryDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultsEveryDay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultsEveryDay))
            )
            .andExpect(status().isOk());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
        ResultsEveryDay testResultsEveryDay = resultsEveryDayList.get(resultsEveryDayList.size() - 1);
        assertThat(testResultsEveryDay.getResultDate()).isEqualTo(DEFAULT_RESULT_DATE);
        assertThat(testResultsEveryDay.getPrizeCode()).isEqualTo(DEFAULT_PRIZE_CODE);
        assertThat(testResultsEveryDay.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    void fullUpdateResultsEveryDayWithPatch() throws Exception {
        // Initialize the database
        resultsEveryDayRepository.saveAndFlush(resultsEveryDay);

        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();

        // Update the resultsEveryDay using partial update
        ResultsEveryDay partialUpdatedResultsEveryDay = new ResultsEveryDay();
        partialUpdatedResultsEveryDay.setId(resultsEveryDay.getId());

        partialUpdatedResultsEveryDay.resultDate(UPDATED_RESULT_DATE).prizeCode(UPDATED_PRIZE_CODE).result(UPDATED_RESULT);

        restResultsEveryDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResultsEveryDay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResultsEveryDay))
            )
            .andExpect(status().isOk());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
        ResultsEveryDay testResultsEveryDay = resultsEveryDayList.get(resultsEveryDayList.size() - 1);
        assertThat(testResultsEveryDay.getResultDate()).isEqualTo(UPDATED_RESULT_DATE);
        assertThat(testResultsEveryDay.getPrizeCode()).isEqualTo(UPDATED_PRIZE_CODE);
        assertThat(testResultsEveryDay.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void patchNonExistingResultsEveryDay() throws Exception {
        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();
        resultsEveryDay.setId(longCount.incrementAndGet());

        // Create the ResultsEveryDay
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultsEveryDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resultsEveryDayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResultsEveryDay() throws Exception {
        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();
        resultsEveryDay.setId(longCount.incrementAndGet());

        // Create the ResultsEveryDay
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultsEveryDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResultsEveryDay() throws Exception {
        int databaseSizeBeforeUpdate = resultsEveryDayRepository.findAll().size();
        resultsEveryDay.setId(longCount.incrementAndGet());

        // Create the ResultsEveryDay
        ResultsEveryDayDTO resultsEveryDayDTO = resultsEveryDayMapper.toDto(resultsEveryDay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResultsEveryDayMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resultsEveryDayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResultsEveryDay in the database
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResultsEveryDay() throws Exception {
        // Initialize the database
        resultsEveryDayRepository.saveAndFlush(resultsEveryDay);

        int databaseSizeBeforeDelete = resultsEveryDayRepository.findAll().size();

        // Delete the resultsEveryDay
        restResultsEveryDayMockMvc
            .perform(delete(ENTITY_API_URL_ID, resultsEveryDay.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultsEveryDay> resultsEveryDayList = resultsEveryDayRepository.findAll();
        assertThat(resultsEveryDayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
