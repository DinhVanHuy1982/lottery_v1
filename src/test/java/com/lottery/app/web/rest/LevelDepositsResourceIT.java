package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.LevelDeposits;
import com.lottery.app.repository.LevelDepositsRepository;
import com.lottery.app.service.dto.LevelDepositsDTO;
import com.lottery.app.service.mapper.LevelDepositsMapper;
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
 * Integration tests for the {@link LevelDepositsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LevelDepositsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_MIN_PRICE = 1L;
    private static final Long UPDATED_MIN_PRICE = 2L;

    private static final String DEFAULT_UPDATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ARTICLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/level-deposits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelDepositsRepository levelDepositsRepository;

    @Autowired
    private LevelDepositsMapper levelDepositsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelDepositsMockMvc;

    private LevelDeposits levelDeposits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelDeposits createEntity(EntityManager em) {
        LevelDeposits levelDeposits = new LevelDeposits()
            .code(DEFAULT_CODE)
            .minPrice(DEFAULT_MIN_PRICE)
            .updateName(DEFAULT_UPDATE_NAME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .articleCode(DEFAULT_ARTICLE_CODE);
        return levelDeposits;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelDeposits createUpdatedEntity(EntityManager em) {
        LevelDeposits levelDeposits = new LevelDeposits()
            .code(UPDATED_CODE)
            .minPrice(UPDATED_MIN_PRICE)
            .updateName(UPDATED_UPDATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .articleCode(UPDATED_ARTICLE_CODE);
        return levelDeposits;
    }

    @BeforeEach
    public void initTest() {
        levelDeposits = createEntity(em);
    }

    @Test
    @Transactional
    void createLevelDeposits() throws Exception {
        int databaseSizeBeforeCreate = levelDepositsRepository.findAll().size();
        // Create the LevelDeposits
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);
        restLevelDepositsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeCreate + 1);
        LevelDeposits testLevelDeposits = levelDepositsList.get(levelDepositsList.size() - 1);
        assertThat(testLevelDeposits.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLevelDeposits.getMinPrice()).isEqualTo(DEFAULT_MIN_PRICE);
        assertThat(testLevelDeposits.getUpdateName()).isEqualTo(DEFAULT_UPDATE_NAME);
        assertThat(testLevelDeposits.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testLevelDeposits.getArticleCode()).isEqualTo(DEFAULT_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void createLevelDepositsWithExistingId() throws Exception {
        // Create the LevelDeposits with an existing ID
        levelDeposits.setId(1L);
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);

        int databaseSizeBeforeCreate = levelDepositsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelDepositsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLevelDeposits() throws Exception {
        // Initialize the database
        levelDepositsRepository.saveAndFlush(levelDeposits);

        // Get all the levelDepositsList
        restLevelDepositsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelDeposits.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].minPrice").value(hasItem(DEFAULT_MIN_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].updateName").value(hasItem(DEFAULT_UPDATE_NAME)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].articleCode").value(hasItem(DEFAULT_ARTICLE_CODE)));
    }

    @Test
    @Transactional
    void getLevelDeposits() throws Exception {
        // Initialize the database
        levelDepositsRepository.saveAndFlush(levelDeposits);

        // Get the levelDeposits
        restLevelDepositsMockMvc
            .perform(get(ENTITY_API_URL_ID, levelDeposits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelDeposits.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.minPrice").value(DEFAULT_MIN_PRICE.intValue()))
            .andExpect(jsonPath("$.updateName").value(DEFAULT_UPDATE_NAME))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.articleCode").value(DEFAULT_ARTICLE_CODE));
    }

    @Test
    @Transactional
    void getNonExistingLevelDeposits() throws Exception {
        // Get the levelDeposits
        restLevelDepositsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevelDeposits() throws Exception {
        // Initialize the database
        levelDepositsRepository.saveAndFlush(levelDeposits);

        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();

        // Update the levelDeposits
        LevelDeposits updatedLevelDeposits = levelDepositsRepository.findById(levelDeposits.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLevelDeposits are not directly saved in db
        em.detach(updatedLevelDeposits);
        updatedLevelDeposits
            .code(UPDATED_CODE)
            .minPrice(UPDATED_MIN_PRICE)
            .updateName(UPDATED_UPDATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .articleCode(UPDATED_ARTICLE_CODE);
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(updatedLevelDeposits);

        restLevelDepositsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelDepositsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
        LevelDeposits testLevelDeposits = levelDepositsList.get(levelDepositsList.size() - 1);
        assertThat(testLevelDeposits.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLevelDeposits.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testLevelDeposits.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testLevelDeposits.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testLevelDeposits.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void putNonExistingLevelDeposits() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();
        levelDeposits.setId(longCount.incrementAndGet());

        // Create the LevelDeposits
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelDepositsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelDepositsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevelDeposits() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();
        levelDeposits.setId(longCount.incrementAndGet());

        // Create the LevelDeposits
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevelDeposits() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();
        levelDeposits.setId(longCount.incrementAndGet());

        // Create the LevelDeposits
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLevelDepositsWithPatch() throws Exception {
        // Initialize the database
        levelDepositsRepository.saveAndFlush(levelDeposits);

        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();

        // Update the levelDeposits using partial update
        LevelDeposits partialUpdatedLevelDeposits = new LevelDeposits();
        partialUpdatedLevelDeposits.setId(levelDeposits.getId());

        partialUpdatedLevelDeposits
            .minPrice(UPDATED_MIN_PRICE)
            .updateName(UPDATED_UPDATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .articleCode(UPDATED_ARTICLE_CODE);

        restLevelDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelDeposits.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelDeposits))
            )
            .andExpect(status().isOk());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
        LevelDeposits testLevelDeposits = levelDepositsList.get(levelDepositsList.size() - 1);
        assertThat(testLevelDeposits.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLevelDeposits.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testLevelDeposits.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testLevelDeposits.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testLevelDeposits.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void fullUpdateLevelDepositsWithPatch() throws Exception {
        // Initialize the database
        levelDepositsRepository.saveAndFlush(levelDeposits);

        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();

        // Update the levelDeposits using partial update
        LevelDeposits partialUpdatedLevelDeposits = new LevelDeposits();
        partialUpdatedLevelDeposits.setId(levelDeposits.getId());

        partialUpdatedLevelDeposits
            .code(UPDATED_CODE)
            .minPrice(UPDATED_MIN_PRICE)
            .updateName(UPDATED_UPDATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .articleCode(UPDATED_ARTICLE_CODE);

        restLevelDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelDeposits.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelDeposits))
            )
            .andExpect(status().isOk());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
        LevelDeposits testLevelDeposits = levelDepositsList.get(levelDepositsList.size() - 1);
        assertThat(testLevelDeposits.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLevelDeposits.getMinPrice()).isEqualTo(UPDATED_MIN_PRICE);
        assertThat(testLevelDeposits.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testLevelDeposits.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testLevelDeposits.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingLevelDeposits() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();
        levelDeposits.setId(longCount.incrementAndGet());

        // Create the LevelDeposits
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelDepositsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevelDeposits() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();
        levelDeposits.setId(longCount.incrementAndGet());

        // Create the LevelDeposits
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevelDeposits() throws Exception {
        int databaseSizeBeforeUpdate = levelDepositsRepository.findAll().size();
        levelDeposits.setId(longCount.incrementAndGet());

        // Create the LevelDeposits
        LevelDepositsDTO levelDepositsDTO = levelDepositsMapper.toDto(levelDeposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDepositsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelDeposits in the database
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevelDeposits() throws Exception {
        // Initialize the database
        levelDepositsRepository.saveAndFlush(levelDeposits);

        int databaseSizeBeforeDelete = levelDepositsRepository.findAll().size();

        // Delete the levelDeposits
        restLevelDepositsMockMvc
            .perform(delete(ENTITY_API_URL_ID, levelDeposits.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelDeposits> levelDepositsList = levelDepositsRepository.findAll();
        assertThat(levelDepositsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
