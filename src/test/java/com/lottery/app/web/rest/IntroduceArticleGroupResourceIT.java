package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.IntroduceArticleGroup;
import com.lottery.app.repository.IntroduceArticleGroupRepository;
import com.lottery.app.service.dto.IntroduceArticleGroupDTO;
import com.lottery.app.service.mapper.IntroduceArticleGroupMapper;
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
 * Integration tests for the {@link IntroduceArticleGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntroduceArticleGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_GROUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_INTRODUCE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_INTRODUCE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_INTRODUCE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_INTRODUCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/introduce-article-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntroduceArticleGroupRepository introduceArticleGroupRepository;

    @Autowired
    private IntroduceArticleGroupMapper introduceArticleGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntroduceArticleGroupMockMvc;

    private IntroduceArticleGroup introduceArticleGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntroduceArticleGroup createEntity(EntityManager em) {
        IntroduceArticleGroup introduceArticleGroup = new IntroduceArticleGroup()
            .code(DEFAULT_CODE)
            .articleGroupCode(DEFAULT_ARTICLE_GROUP_CODE)
            .fileId(DEFAULT_FILE_ID)
            .titleIntroduce(DEFAULT_TITLE_INTRODUCE)
            .contentIntroduce(DEFAULT_CONTENT_INTRODUCE);
        return introduceArticleGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntroduceArticleGroup createUpdatedEntity(EntityManager em) {
        IntroduceArticleGroup introduceArticleGroup = new IntroduceArticleGroup()
            .code(UPDATED_CODE)
            .articleGroupCode(UPDATED_ARTICLE_GROUP_CODE)
            .fileId(UPDATED_FILE_ID)
            .titleIntroduce(UPDATED_TITLE_INTRODUCE)
            .contentIntroduce(UPDATED_CONTENT_INTRODUCE);
        return introduceArticleGroup;
    }

    @BeforeEach
    public void initTest() {
        introduceArticleGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createIntroduceArticleGroup() throws Exception {
        int databaseSizeBeforeCreate = introduceArticleGroupRepository.findAll().size();
        // Create the IntroduceArticleGroup
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);
        restIntroduceArticleGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeCreate + 1);
        IntroduceArticleGroup testIntroduceArticleGroup = introduceArticleGroupList.get(introduceArticleGroupList.size() - 1);
        assertThat(testIntroduceArticleGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIntroduceArticleGroup.getArticleGroupCode()).isEqualTo(DEFAULT_ARTICLE_GROUP_CODE);
        assertThat(testIntroduceArticleGroup.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testIntroduceArticleGroup.getTitleIntroduce()).isEqualTo(DEFAULT_TITLE_INTRODUCE);
        assertThat(testIntroduceArticleGroup.getContentIntroduce()).isEqualTo(DEFAULT_CONTENT_INTRODUCE);
    }

    @Test
    @Transactional
    void createIntroduceArticleGroupWithExistingId() throws Exception {
        // Create the IntroduceArticleGroup with an existing ID
        introduceArticleGroup.setId(1L);
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        int databaseSizeBeforeCreate = introduceArticleGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntroduceArticleGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = introduceArticleGroupRepository.findAll().size();
        // set the field null
        introduceArticleGroup.setCode(null);

        // Create the IntroduceArticleGroup, which fails.
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        restIntroduceArticleGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIntroduceArticleGroups() throws Exception {
        // Initialize the database
        introduceArticleGroupRepository.saveAndFlush(introduceArticleGroup);

        // Get all the introduceArticleGroupList
        restIntroduceArticleGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(introduceArticleGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].articleGroupCode").value(hasItem(DEFAULT_ARTICLE_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].titleIntroduce").value(hasItem(DEFAULT_TITLE_INTRODUCE)))
            .andExpect(jsonPath("$.[*].contentIntroduce").value(hasItem(DEFAULT_CONTENT_INTRODUCE)));
    }

    @Test
    @Transactional
    void getIntroduceArticleGroup() throws Exception {
        // Initialize the database
        introduceArticleGroupRepository.saveAndFlush(introduceArticleGroup);

        // Get the introduceArticleGroup
        restIntroduceArticleGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, introduceArticleGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(introduceArticleGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.articleGroupCode").value(DEFAULT_ARTICLE_GROUP_CODE))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.titleIntroduce").value(DEFAULT_TITLE_INTRODUCE))
            .andExpect(jsonPath("$.contentIntroduce").value(DEFAULT_CONTENT_INTRODUCE));
    }

    @Test
    @Transactional
    void getNonExistingIntroduceArticleGroup() throws Exception {
        // Get the introduceArticleGroup
        restIntroduceArticleGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntroduceArticleGroup() throws Exception {
        // Initialize the database
        introduceArticleGroupRepository.saveAndFlush(introduceArticleGroup);

        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();

        // Update the introduceArticleGroup
        IntroduceArticleGroup updatedIntroduceArticleGroup = introduceArticleGroupRepository
            .findById(introduceArticleGroup.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedIntroduceArticleGroup are not directly saved in db
        em.detach(updatedIntroduceArticleGroup);
        updatedIntroduceArticleGroup
            .code(UPDATED_CODE)
            .articleGroupCode(UPDATED_ARTICLE_GROUP_CODE)
            .fileId(UPDATED_FILE_ID)
            .titleIntroduce(UPDATED_TITLE_INTRODUCE)
            .contentIntroduce(UPDATED_CONTENT_INTRODUCE);
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(updatedIntroduceArticleGroup);

        restIntroduceArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, introduceArticleGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
        IntroduceArticleGroup testIntroduceArticleGroup = introduceArticleGroupList.get(introduceArticleGroupList.size() - 1);
        assertThat(testIntroduceArticleGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIntroduceArticleGroup.getArticleGroupCode()).isEqualTo(UPDATED_ARTICLE_GROUP_CODE);
        assertThat(testIntroduceArticleGroup.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testIntroduceArticleGroup.getTitleIntroduce()).isEqualTo(UPDATED_TITLE_INTRODUCE);
        assertThat(testIntroduceArticleGroup.getContentIntroduce()).isEqualTo(UPDATED_CONTENT_INTRODUCE);
    }

    @Test
    @Transactional
    void putNonExistingIntroduceArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();
        introduceArticleGroup.setId(longCount.incrementAndGet());

        // Create the IntroduceArticleGroup
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntroduceArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, introduceArticleGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntroduceArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();
        introduceArticleGroup.setId(longCount.incrementAndGet());

        // Create the IntroduceArticleGroup
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntroduceArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();
        introduceArticleGroup.setId(longCount.incrementAndGet());

        // Create the IntroduceArticleGroup
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntroduceArticleGroupWithPatch() throws Exception {
        // Initialize the database
        introduceArticleGroupRepository.saveAndFlush(introduceArticleGroup);

        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();

        // Update the introduceArticleGroup using partial update
        IntroduceArticleGroup partialUpdatedIntroduceArticleGroup = new IntroduceArticleGroup();
        partialUpdatedIntroduceArticleGroup.setId(introduceArticleGroup.getId());

        partialUpdatedIntroduceArticleGroup
            .code(UPDATED_CODE)
            .articleGroupCode(UPDATED_ARTICLE_GROUP_CODE)
            .fileId(UPDATED_FILE_ID)
            .titleIntroduce(UPDATED_TITLE_INTRODUCE);

        restIntroduceArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntroduceArticleGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntroduceArticleGroup))
            )
            .andExpect(status().isOk());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
        IntroduceArticleGroup testIntroduceArticleGroup = introduceArticleGroupList.get(introduceArticleGroupList.size() - 1);
        assertThat(testIntroduceArticleGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIntroduceArticleGroup.getArticleGroupCode()).isEqualTo(UPDATED_ARTICLE_GROUP_CODE);
        assertThat(testIntroduceArticleGroup.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testIntroduceArticleGroup.getTitleIntroduce()).isEqualTo(UPDATED_TITLE_INTRODUCE);
        assertThat(testIntroduceArticleGroup.getContentIntroduce()).isEqualTo(DEFAULT_CONTENT_INTRODUCE);
    }

    @Test
    @Transactional
    void fullUpdateIntroduceArticleGroupWithPatch() throws Exception {
        // Initialize the database
        introduceArticleGroupRepository.saveAndFlush(introduceArticleGroup);

        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();

        // Update the introduceArticleGroup using partial update
        IntroduceArticleGroup partialUpdatedIntroduceArticleGroup = new IntroduceArticleGroup();
        partialUpdatedIntroduceArticleGroup.setId(introduceArticleGroup.getId());

        partialUpdatedIntroduceArticleGroup
            .code(UPDATED_CODE)
            .articleGroupCode(UPDATED_ARTICLE_GROUP_CODE)
            .fileId(UPDATED_FILE_ID)
            .titleIntroduce(UPDATED_TITLE_INTRODUCE)
            .contentIntroduce(UPDATED_CONTENT_INTRODUCE);

        restIntroduceArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntroduceArticleGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntroduceArticleGroup))
            )
            .andExpect(status().isOk());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
        IntroduceArticleGroup testIntroduceArticleGroup = introduceArticleGroupList.get(introduceArticleGroupList.size() - 1);
        assertThat(testIntroduceArticleGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIntroduceArticleGroup.getArticleGroupCode()).isEqualTo(UPDATED_ARTICLE_GROUP_CODE);
        assertThat(testIntroduceArticleGroup.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testIntroduceArticleGroup.getTitleIntroduce()).isEqualTo(UPDATED_TITLE_INTRODUCE);
        assertThat(testIntroduceArticleGroup.getContentIntroduce()).isEqualTo(UPDATED_CONTENT_INTRODUCE);
    }

    @Test
    @Transactional
    void patchNonExistingIntroduceArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();
        introduceArticleGroup.setId(longCount.incrementAndGet());

        // Create the IntroduceArticleGroup
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntroduceArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, introduceArticleGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntroduceArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();
        introduceArticleGroup.setId(longCount.incrementAndGet());

        // Create the IntroduceArticleGroup
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntroduceArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleGroupRepository.findAll().size();
        introduceArticleGroup.setId(longCount.incrementAndGet());

        // Create the IntroduceArticleGroup
        IntroduceArticleGroupDTO introduceArticleGroupDTO = introduceArticleGroupMapper.toDto(introduceArticleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntroduceArticleGroup in the database
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntroduceArticleGroup() throws Exception {
        // Initialize the database
        introduceArticleGroupRepository.saveAndFlush(introduceArticleGroup);

        int databaseSizeBeforeDelete = introduceArticleGroupRepository.findAll().size();

        // Delete the introduceArticleGroup
        restIntroduceArticleGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, introduceArticleGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntroduceArticleGroup> introduceArticleGroupList = introduceArticleGroupRepository.findAll();
        assertThat(introduceArticleGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
