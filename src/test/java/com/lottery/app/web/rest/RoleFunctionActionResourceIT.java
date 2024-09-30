package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.RoleFunctionAction;
import com.lottery.app.repository.RoleFunctionActionRepository;
import com.lottery.app.service.dto.RoleFunctionActionDTO;
import com.lottery.app.service.mapper.RoleFunctionActionMapper;
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
 * Integration tests for the {@link RoleFunctionActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleFunctionActionResourceIT {

    private static final String DEFAULT_ROLE_FUNCTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_FUNCTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/role-function-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleFunctionActionRepository roleFunctionActionRepository;

    @Autowired
    private RoleFunctionActionMapper roleFunctionActionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleFunctionActionMockMvc;

    private RoleFunctionAction roleFunctionAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleFunctionAction createEntity(EntityManager em) {
        RoleFunctionAction roleFunctionAction = new RoleFunctionAction()
            .roleFunctionCode(DEFAULT_ROLE_FUNCTION_CODE)
            .actionCode(DEFAULT_ACTION_CODE);
        return roleFunctionAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleFunctionAction createUpdatedEntity(EntityManager em) {
        RoleFunctionAction roleFunctionAction = new RoleFunctionAction()
            .roleFunctionCode(UPDATED_ROLE_FUNCTION_CODE)
            .actionCode(UPDATED_ACTION_CODE);
        return roleFunctionAction;
    }

    @BeforeEach
    public void initTest() {
        roleFunctionAction = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleFunctionAction() throws Exception {
        int databaseSizeBeforeCreate = roleFunctionActionRepository.findAll().size();
        // Create the RoleFunctionAction
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);
        restRoleFunctionActionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeCreate + 1);
        RoleFunctionAction testRoleFunctionAction = roleFunctionActionList.get(roleFunctionActionList.size() - 1);
        assertThat(testRoleFunctionAction.getRoleFunctionCode()).isEqualTo(DEFAULT_ROLE_FUNCTION_CODE);
        assertThat(testRoleFunctionAction.getActionCode()).isEqualTo(DEFAULT_ACTION_CODE);
    }

    @Test
    @Transactional
    void createRoleFunctionActionWithExistingId() throws Exception {
        // Create the RoleFunctionAction with an existing ID
        roleFunctionAction.setId(1L);
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);

        int databaseSizeBeforeCreate = roleFunctionActionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleFunctionActionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoleFunctionActions() throws Exception {
        // Initialize the database
        roleFunctionActionRepository.saveAndFlush(roleFunctionAction);

        // Get all the roleFunctionActionList
        restRoleFunctionActionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleFunctionAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleFunctionCode").value(hasItem(DEFAULT_ROLE_FUNCTION_CODE)))
            .andExpect(jsonPath("$.[*].actionCode").value(hasItem(DEFAULT_ACTION_CODE)));
    }

    @Test
    @Transactional
    void getRoleFunctionAction() throws Exception {
        // Initialize the database
        roleFunctionActionRepository.saveAndFlush(roleFunctionAction);

        // Get the roleFunctionAction
        restRoleFunctionActionMockMvc
            .perform(get(ENTITY_API_URL_ID, roleFunctionAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleFunctionAction.getId().intValue()))
            .andExpect(jsonPath("$.roleFunctionCode").value(DEFAULT_ROLE_FUNCTION_CODE))
            .andExpect(jsonPath("$.actionCode").value(DEFAULT_ACTION_CODE));
    }

    @Test
    @Transactional
    void getNonExistingRoleFunctionAction() throws Exception {
        // Get the roleFunctionAction
        restRoleFunctionActionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoleFunctionAction() throws Exception {
        // Initialize the database
        roleFunctionActionRepository.saveAndFlush(roleFunctionAction);

        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();

        // Update the roleFunctionAction
        RoleFunctionAction updatedRoleFunctionAction = roleFunctionActionRepository.findById(roleFunctionAction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRoleFunctionAction are not directly saved in db
        em.detach(updatedRoleFunctionAction);
        updatedRoleFunctionAction.roleFunctionCode(UPDATED_ROLE_FUNCTION_CODE).actionCode(UPDATED_ACTION_CODE);
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(updatedRoleFunctionAction);

        restRoleFunctionActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleFunctionActionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isOk());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
        RoleFunctionAction testRoleFunctionAction = roleFunctionActionList.get(roleFunctionActionList.size() - 1);
        assertThat(testRoleFunctionAction.getRoleFunctionCode()).isEqualTo(UPDATED_ROLE_FUNCTION_CODE);
        assertThat(testRoleFunctionAction.getActionCode()).isEqualTo(UPDATED_ACTION_CODE);
    }

    @Test
    @Transactional
    void putNonExistingRoleFunctionAction() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();
        roleFunctionAction.setId(longCount.incrementAndGet());

        // Create the RoleFunctionAction
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleFunctionActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleFunctionActionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleFunctionAction() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();
        roleFunctionAction.setId(longCount.incrementAndGet());

        // Create the RoleFunctionAction
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleFunctionAction() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();
        roleFunctionAction.setId(longCount.incrementAndGet());

        // Create the RoleFunctionAction
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionActionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleFunctionActionWithPatch() throws Exception {
        // Initialize the database
        roleFunctionActionRepository.saveAndFlush(roleFunctionAction);

        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();

        // Update the roleFunctionAction using partial update
        RoleFunctionAction partialUpdatedRoleFunctionAction = new RoleFunctionAction();
        partialUpdatedRoleFunctionAction.setId(roleFunctionAction.getId());

        partialUpdatedRoleFunctionAction.roleFunctionCode(UPDATED_ROLE_FUNCTION_CODE).actionCode(UPDATED_ACTION_CODE);

        restRoleFunctionActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleFunctionAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleFunctionAction))
            )
            .andExpect(status().isOk());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
        RoleFunctionAction testRoleFunctionAction = roleFunctionActionList.get(roleFunctionActionList.size() - 1);
        assertThat(testRoleFunctionAction.getRoleFunctionCode()).isEqualTo(UPDATED_ROLE_FUNCTION_CODE);
        assertThat(testRoleFunctionAction.getActionCode()).isEqualTo(UPDATED_ACTION_CODE);
    }

    @Test
    @Transactional
    void fullUpdateRoleFunctionActionWithPatch() throws Exception {
        // Initialize the database
        roleFunctionActionRepository.saveAndFlush(roleFunctionAction);

        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();

        // Update the roleFunctionAction using partial update
        RoleFunctionAction partialUpdatedRoleFunctionAction = new RoleFunctionAction();
        partialUpdatedRoleFunctionAction.setId(roleFunctionAction.getId());

        partialUpdatedRoleFunctionAction.roleFunctionCode(UPDATED_ROLE_FUNCTION_CODE).actionCode(UPDATED_ACTION_CODE);

        restRoleFunctionActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleFunctionAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleFunctionAction))
            )
            .andExpect(status().isOk());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
        RoleFunctionAction testRoleFunctionAction = roleFunctionActionList.get(roleFunctionActionList.size() - 1);
        assertThat(testRoleFunctionAction.getRoleFunctionCode()).isEqualTo(UPDATED_ROLE_FUNCTION_CODE);
        assertThat(testRoleFunctionAction.getActionCode()).isEqualTo(UPDATED_ACTION_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingRoleFunctionAction() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();
        roleFunctionAction.setId(longCount.incrementAndGet());

        // Create the RoleFunctionAction
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleFunctionActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleFunctionActionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleFunctionAction() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();
        roleFunctionAction.setId(longCount.incrementAndGet());

        // Create the RoleFunctionAction
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleFunctionAction() throws Exception {
        int databaseSizeBeforeUpdate = roleFunctionActionRepository.findAll().size();
        roleFunctionAction.setId(longCount.incrementAndGet());

        // Create the RoleFunctionAction
        RoleFunctionActionDTO roleFunctionActionDTO = roleFunctionActionMapper.toDto(roleFunctionAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleFunctionActionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleFunctionActionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleFunctionAction in the database
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleFunctionAction() throws Exception {
        // Initialize the database
        roleFunctionActionRepository.saveAndFlush(roleFunctionAction);

        int databaseSizeBeforeDelete = roleFunctionActionRepository.findAll().size();

        // Delete the roleFunctionAction
        restRoleFunctionActionMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleFunctionAction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleFunctionAction> roleFunctionActionList = roleFunctionActionRepository.findAll();
        assertThat(roleFunctionActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
