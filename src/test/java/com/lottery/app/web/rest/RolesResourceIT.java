package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.Roles;
import com.lottery.app.repository.RolesRepository;
import com.lottery.app.service.dto.RolesDTO;
import com.lottery.app.service.mapper.RolesMapper;
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
 * Integration tests for the {@link RolesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RolesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRolesMockMvc;

    private Roles roles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roles createEntity(EntityManager em) {
        Roles roles = new Roles()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .createTime(DEFAULT_CREATE_TIME)
            .createName(DEFAULT_CREATE_NAME)
            .updateName(DEFAULT_UPDATE_NAME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return roles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roles createUpdatedEntity(EntityManager em) {
        Roles roles = new Roles()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .createTime(UPDATED_CREATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateName(UPDATED_UPDATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME);
        return roles;
    }

    @BeforeEach
    public void initTest() {
        roles = createEntity(em);
    }

    @Test
    @Transactional
    void createRoles() throws Exception {
        int databaseSizeBeforeCreate = rolesRepository.findAll().size();
        // Create the Roles
        RolesDTO rolesDTO = rolesMapper.toDto(roles);
        restRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rolesDTO)))
            .andExpect(status().isCreated());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate + 1);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoles.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRoles.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRoles.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoles.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testRoles.getCreateName()).isEqualTo(DEFAULT_CREATE_NAME);
        assertThat(testRoles.getUpdateName()).isEqualTo(DEFAULT_UPDATE_NAME);
        assertThat(testRoles.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void createRolesWithExistingId() throws Exception {
        // Create the Roles with an existing ID
        roles.setId(1L);
        RolesDTO rolesDTO = rolesMapper.toDto(roles);

        int databaseSizeBeforeCreate = rolesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rolesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList
        restRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roles.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createName").value(hasItem(DEFAULT_CREATE_NAME)))
            .andExpect(jsonPath("$.[*].updateName").value(hasItem(DEFAULT_UPDATE_NAME)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get the roles
        restRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, roles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roles.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.createName").value(DEFAULT_CREATE_NAME))
            .andExpect(jsonPath("$.updateName").value(DEFAULT_UPDATE_NAME))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRoles() throws Exception {
        // Get the roles
        restRolesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles
        Roles updatedRoles = rolesRepository.findById(roles.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRoles are not directly saved in db
        em.detach(updatedRoles);
        updatedRoles
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .createTime(UPDATED_CREATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateName(UPDATED_UPDATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME);
        RolesDTO rolesDTO = rolesMapper.toDto(updatedRoles);

        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rolesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoles.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRoles.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoles.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRoles.getCreateName()).isEqualTo(UPDATED_CREATE_NAME);
        assertThat(testRoles.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testRoles.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(longCount.incrementAndGet());

        // Create the Roles
        RolesDTO rolesDTO = rolesMapper.toDto(roles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rolesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(longCount.incrementAndGet());

        // Create the Roles
        RolesDTO rolesDTO = rolesMapper.toDto(roles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(longCount.incrementAndGet());

        // Create the Roles
        RolesDTO rolesDTO = rolesMapper.toDto(roles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rolesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRolesWithPatch() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles using partial update
        Roles partialUpdatedRoles = new Roles();
        partialUpdatedRoles.setId(roles.getId());

        partialUpdatedRoles
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoles.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRoles.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoles.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRoles.getCreateName()).isEqualTo(DEFAULT_CREATE_NAME);
        assertThat(testRoles.getUpdateName()).isEqualTo(DEFAULT_UPDATE_NAME);
        assertThat(testRoles.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateRolesWithPatch() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles using partial update
        Roles partialUpdatedRoles = new Roles();
        partialUpdatedRoles.setId(roles.getId());

        partialUpdatedRoles
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .createTime(UPDATED_CREATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateName(UPDATED_UPDATE_NAME)
            .updateTime(UPDATED_UPDATE_TIME);

        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoles))
            )
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoles.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRoles.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoles.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRoles.getCreateName()).isEqualTo(UPDATED_CREATE_NAME);
        assertThat(testRoles.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testRoles.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(longCount.incrementAndGet());

        // Create the Roles
        RolesDTO rolesDTO = rolesMapper.toDto(roles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rolesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(longCount.incrementAndGet());

        // Create the Roles
        RolesDTO rolesDTO = rolesMapper.toDto(roles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();
        roles.setId(longCount.incrementAndGet());

        // Create the Roles
        RolesDTO rolesDTO = rolesMapper.toDto(roles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRolesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rolesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        int databaseSizeBeforeDelete = rolesRepository.findAll().size();

        // Delete the roles
        restRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, roles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
