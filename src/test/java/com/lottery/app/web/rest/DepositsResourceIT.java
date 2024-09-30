package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.Deposits;
import com.lottery.app.repository.DepositsRepository;
import com.lottery.app.service.dto.DepositsDTO;
import com.lottery.app.service.mapper.DepositsMapper;
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
 * Integration tests for the {@link DepositsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepositsResourceIT {

    private static final String DEFAULT_ARTICLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NETWROK_CARD = "AAAAAAAAAA";
    private static final String UPDATED_NETWROK_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_CARD = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_CARD = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SERI_CARD = "AAAAAAAAAA";
    private static final String UPDATED_SERI_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_CARD = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CARD = "BBBBBBBBBB";

    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;

    private static final String DEFAULT_USER_APPOSE = "AAAAAAAAAA";
    private static final String UPDATED_USER_APPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_CHOICE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_CHOICE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deposits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepositsRepository depositsRepository;

    @Autowired
    private DepositsMapper depositsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepositsMockMvc;

    private Deposits deposits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposits createEntity(EntityManager em) {
        Deposits deposits = new Deposits()
            .articleCode(DEFAULT_ARTICLE_CODE)
            .netwrokCard(DEFAULT_NETWROK_CARD)
            .valueCard(DEFAULT_VALUE_CARD)
            .createTime(DEFAULT_CREATE_TIME)
            .seriCard(DEFAULT_SERI_CARD)
            .codeCard(DEFAULT_CODE_CARD)
            .status(DEFAULT_STATUS)
            .userAppose(DEFAULT_USER_APPOSE)
            .valueChoice(DEFAULT_VALUE_CHOICE)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return deposits;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposits createUpdatedEntity(EntityManager em) {
        Deposits deposits = new Deposits()
            .articleCode(UPDATED_ARTICLE_CODE)
            .netwrokCard(UPDATED_NETWROK_CARD)
            .valueCard(UPDATED_VALUE_CARD)
            .createTime(UPDATED_CREATE_TIME)
            .seriCard(UPDATED_SERI_CARD)
            .codeCard(UPDATED_CODE_CARD)
            .status(UPDATED_STATUS)
            .userAppose(UPDATED_USER_APPOSE)
            .valueChoice(UPDATED_VALUE_CHOICE)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return deposits;
    }

    @BeforeEach
    public void initTest() {
        deposits = createEntity(em);
    }

    @Test
    @Transactional
    void createDeposits() throws Exception {
        int databaseSizeBeforeCreate = depositsRepository.findAll().size();
        // Create the Deposits
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);
        restDepositsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositsDTO)))
            .andExpect(status().isCreated());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeCreate + 1);
        Deposits testDeposits = depositsList.get(depositsList.size() - 1);
        assertThat(testDeposits.getArticleCode()).isEqualTo(DEFAULT_ARTICLE_CODE);
        assertThat(testDeposits.getNetwrokCard()).isEqualTo(DEFAULT_NETWROK_CARD);
        assertThat(testDeposits.getValueCard()).isEqualTo(DEFAULT_VALUE_CARD);
        assertThat(testDeposits.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testDeposits.getSeriCard()).isEqualTo(DEFAULT_SERI_CARD);
        assertThat(testDeposits.getCodeCard()).isEqualTo(DEFAULT_CODE_CARD);
        assertThat(testDeposits.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeposits.getUserAppose()).isEqualTo(DEFAULT_USER_APPOSE);
        assertThat(testDeposits.getValueChoice()).isEqualTo(DEFAULT_VALUE_CHOICE);
        assertThat(testDeposits.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void createDepositsWithExistingId() throws Exception {
        // Create the Deposits with an existing ID
        deposits.setId(1L);
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);

        int databaseSizeBeforeCreate = depositsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeposits() throws Exception {
        // Initialize the database
        depositsRepository.saveAndFlush(deposits);

        // Get all the depositsList
        restDepositsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deposits.getId().intValue())))
            .andExpect(jsonPath("$.[*].articleCode").value(hasItem(DEFAULT_ARTICLE_CODE)))
            .andExpect(jsonPath("$.[*].netwrokCard").value(hasItem(DEFAULT_NETWROK_CARD)))
            .andExpect(jsonPath("$.[*].valueCard").value(hasItem(DEFAULT_VALUE_CARD)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].seriCard").value(hasItem(DEFAULT_SERI_CARD)))
            .andExpect(jsonPath("$.[*].codeCard").value(hasItem(DEFAULT_CODE_CARD)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].userAppose").value(hasItem(DEFAULT_USER_APPOSE)))
            .andExpect(jsonPath("$.[*].valueChoice").value(hasItem(DEFAULT_VALUE_CHOICE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getDeposits() throws Exception {
        // Initialize the database
        depositsRepository.saveAndFlush(deposits);

        // Get the deposits
        restDepositsMockMvc
            .perform(get(ENTITY_API_URL_ID, deposits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deposits.getId().intValue()))
            .andExpect(jsonPath("$.articleCode").value(DEFAULT_ARTICLE_CODE))
            .andExpect(jsonPath("$.netwrokCard").value(DEFAULT_NETWROK_CARD))
            .andExpect(jsonPath("$.valueCard").value(DEFAULT_VALUE_CARD))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.seriCard").value(DEFAULT_SERI_CARD))
            .andExpect(jsonPath("$.codeCard").value(DEFAULT_CODE_CARD))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()))
            .andExpect(jsonPath("$.userAppose").value(DEFAULT_USER_APPOSE))
            .andExpect(jsonPath("$.valueChoice").value(DEFAULT_VALUE_CHOICE))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingDeposits() throws Exception {
        // Get the deposits
        restDepositsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeposits() throws Exception {
        // Initialize the database
        depositsRepository.saveAndFlush(deposits);

        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();

        // Update the deposits
        Deposits updatedDeposits = depositsRepository.findById(deposits.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeposits are not directly saved in db
        em.detach(updatedDeposits);
        updatedDeposits
            .articleCode(UPDATED_ARTICLE_CODE)
            .netwrokCard(UPDATED_NETWROK_CARD)
            .valueCard(UPDATED_VALUE_CARD)
            .createTime(UPDATED_CREATE_TIME)
            .seriCard(UPDATED_SERI_CARD)
            .codeCard(UPDATED_CODE_CARD)
            .status(UPDATED_STATUS)
            .userAppose(UPDATED_USER_APPOSE)
            .valueChoice(UPDATED_VALUE_CHOICE)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        DepositsDTO depositsDTO = depositsMapper.toDto(updatedDeposits);

        restDepositsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, depositsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depositsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
        Deposits testDeposits = depositsList.get(depositsList.size() - 1);
        assertThat(testDeposits.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testDeposits.getNetwrokCard()).isEqualTo(UPDATED_NETWROK_CARD);
        assertThat(testDeposits.getValueCard()).isEqualTo(UPDATED_VALUE_CARD);
        assertThat(testDeposits.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDeposits.getSeriCard()).isEqualTo(UPDATED_SERI_CARD);
        assertThat(testDeposits.getCodeCard()).isEqualTo(UPDATED_CODE_CARD);
        assertThat(testDeposits.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeposits.getUserAppose()).isEqualTo(UPDATED_USER_APPOSE);
        assertThat(testDeposits.getValueChoice()).isEqualTo(UPDATED_VALUE_CHOICE);
        assertThat(testDeposits.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingDeposits() throws Exception {
        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();
        deposits.setId(longCount.incrementAndGet());

        // Create the Deposits
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, depositsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeposits() throws Exception {
        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();
        deposits.setId(longCount.incrementAndGet());

        // Create the Deposits
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeposits() throws Exception {
        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();
        deposits.setId(longCount.incrementAndGet());

        // Create the Deposits
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depositsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepositsWithPatch() throws Exception {
        // Initialize the database
        depositsRepository.saveAndFlush(deposits);

        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();

        // Update the deposits using partial update
        Deposits partialUpdatedDeposits = new Deposits();
        partialUpdatedDeposits.setId(deposits.getId());

        partialUpdatedDeposits
            .valueCard(UPDATED_VALUE_CARD)
            .createTime(UPDATED_CREATE_TIME)
            .seriCard(UPDATED_SERI_CARD)
            .codeCard(UPDATED_CODE_CARD)
            .valueChoice(UPDATED_VALUE_CHOICE);

        restDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeposits.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeposits))
            )
            .andExpect(status().isOk());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
        Deposits testDeposits = depositsList.get(depositsList.size() - 1);
        assertThat(testDeposits.getArticleCode()).isEqualTo(DEFAULT_ARTICLE_CODE);
        assertThat(testDeposits.getNetwrokCard()).isEqualTo(DEFAULT_NETWROK_CARD);
        assertThat(testDeposits.getValueCard()).isEqualTo(UPDATED_VALUE_CARD);
        assertThat(testDeposits.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDeposits.getSeriCard()).isEqualTo(UPDATED_SERI_CARD);
        assertThat(testDeposits.getCodeCard()).isEqualTo(UPDATED_CODE_CARD);
        assertThat(testDeposits.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeposits.getUserAppose()).isEqualTo(DEFAULT_USER_APPOSE);
        assertThat(testDeposits.getValueChoice()).isEqualTo(UPDATED_VALUE_CHOICE);
        assertThat(testDeposits.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateDepositsWithPatch() throws Exception {
        // Initialize the database
        depositsRepository.saveAndFlush(deposits);

        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();

        // Update the deposits using partial update
        Deposits partialUpdatedDeposits = new Deposits();
        partialUpdatedDeposits.setId(deposits.getId());

        partialUpdatedDeposits
            .articleCode(UPDATED_ARTICLE_CODE)
            .netwrokCard(UPDATED_NETWROK_CARD)
            .valueCard(UPDATED_VALUE_CARD)
            .createTime(UPDATED_CREATE_TIME)
            .seriCard(UPDATED_SERI_CARD)
            .codeCard(UPDATED_CODE_CARD)
            .status(UPDATED_STATUS)
            .userAppose(UPDATED_USER_APPOSE)
            .valueChoice(UPDATED_VALUE_CHOICE)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeposits.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeposits))
            )
            .andExpect(status().isOk());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
        Deposits testDeposits = depositsList.get(depositsList.size() - 1);
        assertThat(testDeposits.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testDeposits.getNetwrokCard()).isEqualTo(UPDATED_NETWROK_CARD);
        assertThat(testDeposits.getValueCard()).isEqualTo(UPDATED_VALUE_CARD);
        assertThat(testDeposits.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDeposits.getSeriCard()).isEqualTo(UPDATED_SERI_CARD);
        assertThat(testDeposits.getCodeCard()).isEqualTo(UPDATED_CODE_CARD);
        assertThat(testDeposits.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeposits.getUserAppose()).isEqualTo(UPDATED_USER_APPOSE);
        assertThat(testDeposits.getValueChoice()).isEqualTo(UPDATED_VALUE_CHOICE);
        assertThat(testDeposits.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingDeposits() throws Exception {
        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();
        deposits.setId(longCount.incrementAndGet());

        // Create the Deposits
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, depositsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeposits() throws Exception {
        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();
        deposits.setId(longCount.incrementAndGet());

        // Create the Deposits
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depositsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeposits() throws Exception {
        int databaseSizeBeforeUpdate = depositsRepository.findAll().size();
        deposits.setId(longCount.incrementAndGet());

        // Create the Deposits
        DepositsDTO depositsDTO = depositsMapper.toDto(deposits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(depositsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deposits in the database
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeposits() throws Exception {
        // Initialize the database
        depositsRepository.saveAndFlush(deposits);

        int databaseSizeBeforeDelete = depositsRepository.findAll().size();

        // Delete the deposits
        restDepositsMockMvc
            .perform(delete(ENTITY_API_URL_ID, deposits.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deposits> depositsList = depositsRepository.findAll();
        assertThat(depositsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
