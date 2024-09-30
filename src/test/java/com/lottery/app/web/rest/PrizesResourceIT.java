package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.Prizes;
import com.lottery.app.repository.PrizesRepository;
import com.lottery.app.service.dto.PrizesDTO;
import com.lottery.app.service.mapper.PrizesMapper;
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
 * Integration tests for the {@link PrizesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrizesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL_CUP = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL_CUP = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_PRIZE = 1L;
    private static final Long UPDATED_NUMBER_PRIZE = 2L;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prizes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrizesRepository prizesRepository;

    @Autowired
    private PrizesMapper prizesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrizesMockMvc;

    private Prizes prizes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prizes createEntity(EntityManager em) {
        Prizes prizes = new Prizes()
            .code(DEFAULT_CODE)
            .articleCode(DEFAULT_ARTICLE_CODE)
            .levelCup(DEFAULT_LEVEL_CUP)
            .numberPrize(DEFAULT_NUMBER_PRIZE)
            .createTime(DEFAULT_CREATE_TIME)
            .createName(DEFAULT_CREATE_NAME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateName(DEFAULT_UPDATE_NAME);
        return prizes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prizes createUpdatedEntity(EntityManager em) {
        Prizes prizes = new Prizes()
            .code(UPDATED_CODE)
            .articleCode(UPDATED_ARTICLE_CODE)
            .levelCup(UPDATED_LEVEL_CUP)
            .numberPrize(UPDATED_NUMBER_PRIZE)
            .createTime(UPDATED_CREATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateName(UPDATED_UPDATE_NAME);
        return prizes;
    }

    @BeforeEach
    public void initTest() {
        prizes = createEntity(em);
    }

    @Test
    @Transactional
    void createPrizes() throws Exception {
        int databaseSizeBeforeCreate = prizesRepository.findAll().size();
        // Create the Prizes
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);
        restPrizesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prizesDTO)))
            .andExpect(status().isCreated());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeCreate + 1);
        Prizes testPrizes = prizesList.get(prizesList.size() - 1);
        assertThat(testPrizes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPrizes.getArticleCode()).isEqualTo(DEFAULT_ARTICLE_CODE);
        assertThat(testPrizes.getLevelCup()).isEqualTo(DEFAULT_LEVEL_CUP);
        assertThat(testPrizes.getNumberPrize()).isEqualTo(DEFAULT_NUMBER_PRIZE);
        assertThat(testPrizes.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPrizes.getCreateName()).isEqualTo(DEFAULT_CREATE_NAME);
        assertThat(testPrizes.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPrizes.getUpdateName()).isEqualTo(DEFAULT_UPDATE_NAME);
    }

    @Test
    @Transactional
    void createPrizesWithExistingId() throws Exception {
        // Create the Prizes with an existing ID
        prizes.setId(1L);
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);

        int databaseSizeBeforeCreate = prizesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrizesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prizesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrizes() throws Exception {
        // Initialize the database
        prizesRepository.saveAndFlush(prizes);

        // Get all the prizesList
        restPrizesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prizes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].articleCode").value(hasItem(DEFAULT_ARTICLE_CODE)))
            .andExpect(jsonPath("$.[*].levelCup").value(hasItem(DEFAULT_LEVEL_CUP)))
            .andExpect(jsonPath("$.[*].numberPrize").value(hasItem(DEFAULT_NUMBER_PRIZE.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createName").value(hasItem(DEFAULT_CREATE_NAME)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateName").value(hasItem(DEFAULT_UPDATE_NAME)));
    }

    @Test
    @Transactional
    void getPrizes() throws Exception {
        // Initialize the database
        prizesRepository.saveAndFlush(prizes);

        // Get the prizes
        restPrizesMockMvc
            .perform(get(ENTITY_API_URL_ID, prizes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prizes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.articleCode").value(DEFAULT_ARTICLE_CODE))
            .andExpect(jsonPath("$.levelCup").value(DEFAULT_LEVEL_CUP))
            .andExpect(jsonPath("$.numberPrize").value(DEFAULT_NUMBER_PRIZE.intValue()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.createName").value(DEFAULT_CREATE_NAME))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateName").value(DEFAULT_UPDATE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPrizes() throws Exception {
        // Get the prizes
        restPrizesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrizes() throws Exception {
        // Initialize the database
        prizesRepository.saveAndFlush(prizes);

        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();

        // Update the prizes
        Prizes updatedPrizes = prizesRepository.findById(prizes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrizes are not directly saved in db
        em.detach(updatedPrizes);
        updatedPrizes
            .code(UPDATED_CODE)
            .articleCode(UPDATED_ARTICLE_CODE)
            .levelCup(UPDATED_LEVEL_CUP)
            .numberPrize(UPDATED_NUMBER_PRIZE)
            .createTime(UPDATED_CREATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateName(UPDATED_UPDATE_NAME);
        PrizesDTO prizesDTO = prizesMapper.toDto(updatedPrizes);

        restPrizesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prizesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prizesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
        Prizes testPrizes = prizesList.get(prizesList.size() - 1);
        assertThat(testPrizes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPrizes.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testPrizes.getLevelCup()).isEqualTo(UPDATED_LEVEL_CUP);
        assertThat(testPrizes.getNumberPrize()).isEqualTo(UPDATED_NUMBER_PRIZE);
        assertThat(testPrizes.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPrizes.getCreateName()).isEqualTo(UPDATED_CREATE_NAME);
        assertThat(testPrizes.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPrizes.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPrizes() throws Exception {
        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();
        prizes.setId(longCount.incrementAndGet());

        // Create the Prizes
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prizesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrizes() throws Exception {
        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();
        prizes.setId(longCount.incrementAndGet());

        // Create the Prizes
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrizes() throws Exception {
        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();
        prizes.setId(longCount.incrementAndGet());

        // Create the Prizes
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prizesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrizesWithPatch() throws Exception {
        // Initialize the database
        prizesRepository.saveAndFlush(prizes);

        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();

        // Update the prizes using partial update
        Prizes partialUpdatedPrizes = new Prizes();
        partialUpdatedPrizes.setId(prizes.getId());

        partialUpdatedPrizes
            .articleCode(UPDATED_ARTICLE_CODE)
            .levelCup(UPDATED_LEVEL_CUP)
            .numberPrize(UPDATED_NUMBER_PRIZE)
            .createTime(UPDATED_CREATE_TIME)
            .updateName(UPDATED_UPDATE_NAME);

        restPrizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrizes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrizes))
            )
            .andExpect(status().isOk());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
        Prizes testPrizes = prizesList.get(prizesList.size() - 1);
        assertThat(testPrizes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPrizes.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testPrizes.getLevelCup()).isEqualTo(UPDATED_LEVEL_CUP);
        assertThat(testPrizes.getNumberPrize()).isEqualTo(UPDATED_NUMBER_PRIZE);
        assertThat(testPrizes.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPrizes.getCreateName()).isEqualTo(DEFAULT_CREATE_NAME);
        assertThat(testPrizes.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPrizes.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePrizesWithPatch() throws Exception {
        // Initialize the database
        prizesRepository.saveAndFlush(prizes);

        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();

        // Update the prizes using partial update
        Prizes partialUpdatedPrizes = new Prizes();
        partialUpdatedPrizes.setId(prizes.getId());

        partialUpdatedPrizes
            .code(UPDATED_CODE)
            .articleCode(UPDATED_ARTICLE_CODE)
            .levelCup(UPDATED_LEVEL_CUP)
            .numberPrize(UPDATED_NUMBER_PRIZE)
            .createTime(UPDATED_CREATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateName(UPDATED_UPDATE_NAME);

        restPrizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrizes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrizes))
            )
            .andExpect(status().isOk());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
        Prizes testPrizes = prizesList.get(prizesList.size() - 1);
        assertThat(testPrizes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPrizes.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testPrizes.getLevelCup()).isEqualTo(UPDATED_LEVEL_CUP);
        assertThat(testPrizes.getNumberPrize()).isEqualTo(UPDATED_NUMBER_PRIZE);
        assertThat(testPrizes.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPrizes.getCreateName()).isEqualTo(UPDATED_CREATE_NAME);
        assertThat(testPrizes.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPrizes.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPrizes() throws Exception {
        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();
        prizes.setId(longCount.incrementAndGet());

        // Create the Prizes
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prizesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrizes() throws Exception {
        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();
        prizes.setId(longCount.incrementAndGet());

        // Create the Prizes
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prizesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrizes() throws Exception {
        int databaseSizeBeforeUpdate = prizesRepository.findAll().size();
        prizes.setId(longCount.incrementAndGet());

        // Create the Prizes
        PrizesDTO prizesDTO = prizesMapper.toDto(prizes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrizesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prizesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prizes in the database
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrizes() throws Exception {
        // Initialize the database
        prizesRepository.saveAndFlush(prizes);

        int databaseSizeBeforeDelete = prizesRepository.findAll().size();

        // Delete the prizes
        restPrizesMockMvc
            .perform(delete(ENTITY_API_URL_ID, prizes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prizes> prizesList = prizesRepository.findAll();
        assertThat(prizesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
