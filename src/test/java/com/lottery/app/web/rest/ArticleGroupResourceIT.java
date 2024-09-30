package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.ArticleGroup;
import com.lottery.app.repository.ArticleGroupRepository;
import com.lottery.app.service.dto.ArticleGroupDTO;
import com.lottery.app.service.mapper.ArticleGroupMapper;
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
 * Integration tests for the {@link ArticleGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/article-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticleGroupRepository articleGroupRepository;

    @Autowired
    private ArticleGroupMapper articleGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleGroupMockMvc;

    private ArticleGroup articleGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleGroup createEntity(EntityManager em) {
        ArticleGroup articleGroup = new ArticleGroup()
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .mainContent(DEFAULT_MAIN_CONTENT)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .createName(DEFAULT_CREATE_NAME)
            .updateName(DEFAULT_UPDATE_NAME)
            .fileName(DEFAULT_FILE_NAME)
            .filePath(DEFAULT_FILE_PATH)
            .fileId(DEFAULT_FILE_ID);
        return articleGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleGroup createUpdatedEntity(EntityManager em) {
        ArticleGroup articleGroup = new ArticleGroup()
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .mainContent(UPDATED_MAIN_CONTENT)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateName(UPDATED_UPDATE_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileId(UPDATED_FILE_ID);
        return articleGroup;
    }

    @BeforeEach
    public void initTest() {
        articleGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createArticleGroup() throws Exception {
        int databaseSizeBeforeCreate = articleGroupRepository.findAll().size();
        // Create the ArticleGroup
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);
        restArticleGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleGroup testArticleGroup = articleGroupList.get(articleGroupList.size() - 1);
        assertThat(testArticleGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testArticleGroup.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testArticleGroup.getMainContent()).isEqualTo(DEFAULT_MAIN_CONTENT);
        assertThat(testArticleGroup.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testArticleGroup.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArticleGroup.getCreateName()).isEqualTo(DEFAULT_CREATE_NAME);
        assertThat(testArticleGroup.getUpdateName()).isEqualTo(DEFAULT_UPDATE_NAME);
        assertThat(testArticleGroup.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testArticleGroup.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testArticleGroup.getFileId()).isEqualTo(DEFAULT_FILE_ID);
    }

    @Test
    @Transactional
    void createArticleGroupWithExistingId() throws Exception {
        // Create the ArticleGroup with an existing ID
        articleGroup.setId(1L);
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        int databaseSizeBeforeCreate = articleGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleGroupRepository.findAll().size();
        // set the field null
        articleGroup.setCode(null);

        // Create the ArticleGroup, which fails.
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        restArticleGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticleGroups() throws Exception {
        // Initialize the database
        articleGroupRepository.saveAndFlush(articleGroup);

        // Get all the articleGroupList
        restArticleGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].mainContent").value(hasItem(DEFAULT_MAIN_CONTENT)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createName").value(hasItem(DEFAULT_CREATE_NAME)))
            .andExpect(jsonPath("$.[*].updateName").value(hasItem(DEFAULT_UPDATE_NAME)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)));
    }

    @Test
    @Transactional
    void getArticleGroup() throws Exception {
        // Initialize the database
        articleGroupRepository.saveAndFlush(articleGroup);

        // Get the articleGroup
        restArticleGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, articleGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.mainContent").value(DEFAULT_MAIN_CONTENT))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.createName").value(DEFAULT_CREATE_NAME))
            .andExpect(jsonPath("$.updateName").value(DEFAULT_UPDATE_NAME))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID));
    }

    @Test
    @Transactional
    void getNonExistingArticleGroup() throws Exception {
        // Get the articleGroup
        restArticleGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticleGroup() throws Exception {
        // Initialize the database
        articleGroupRepository.saveAndFlush(articleGroup);

        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();

        // Update the articleGroup
        ArticleGroup updatedArticleGroup = articleGroupRepository.findById(articleGroup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArticleGroup are not directly saved in db
        em.detach(updatedArticleGroup);
        updatedArticleGroup
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .mainContent(UPDATED_MAIN_CONTENT)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateName(UPDATED_UPDATE_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileId(UPDATED_FILE_ID);
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(updatedArticleGroup);

        restArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
        ArticleGroup testArticleGroup = articleGroupList.get(articleGroupList.size() - 1);
        assertThat(testArticleGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticleGroup.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticleGroup.getMainContent()).isEqualTo(UPDATED_MAIN_CONTENT);
        assertThat(testArticleGroup.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testArticleGroup.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArticleGroup.getCreateName()).isEqualTo(UPDATED_CREATE_NAME);
        assertThat(testArticleGroup.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testArticleGroup.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testArticleGroup.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testArticleGroup.getFileId()).isEqualTo(UPDATED_FILE_ID);
    }

    @Test
    @Transactional
    void putNonExistingArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();
        articleGroup.setId(longCount.incrementAndGet());

        // Create the ArticleGroup
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();
        articleGroup.setId(longCount.incrementAndGet());

        // Create the ArticleGroup
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();
        articleGroup.setId(longCount.incrementAndGet());

        // Create the ArticleGroup
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleGroupMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleGroupWithPatch() throws Exception {
        // Initialize the database
        articleGroupRepository.saveAndFlush(articleGroup);

        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();

        // Update the articleGroup using partial update
        ArticleGroup partialUpdatedArticleGroup = new ArticleGroup();
        partialUpdatedArticleGroup.setId(articleGroup.getId());

        partialUpdatedArticleGroup
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .createName(UPDATED_CREATE_NAME)
            .updateName(UPDATED_UPDATE_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileId(UPDATED_FILE_ID);

        restArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleGroup))
            )
            .andExpect(status().isOk());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
        ArticleGroup testArticleGroup = articleGroupList.get(articleGroupList.size() - 1);
        assertThat(testArticleGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticleGroup.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticleGroup.getMainContent()).isEqualTo(DEFAULT_MAIN_CONTENT);
        assertThat(testArticleGroup.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testArticleGroup.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testArticleGroup.getCreateName()).isEqualTo(UPDATED_CREATE_NAME);
        assertThat(testArticleGroup.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testArticleGroup.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testArticleGroup.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testArticleGroup.getFileId()).isEqualTo(UPDATED_FILE_ID);
    }

    @Test
    @Transactional
    void fullUpdateArticleGroupWithPatch() throws Exception {
        // Initialize the database
        articleGroupRepository.saveAndFlush(articleGroup);

        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();

        // Update the articleGroup using partial update
        ArticleGroup partialUpdatedArticleGroup = new ArticleGroup();
        partialUpdatedArticleGroup.setId(articleGroup.getId());

        partialUpdatedArticleGroup
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .mainContent(UPDATED_MAIN_CONTENT)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .createName(UPDATED_CREATE_NAME)
            .updateName(UPDATED_UPDATE_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileId(UPDATED_FILE_ID);

        restArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleGroup))
            )
            .andExpect(status().isOk());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
        ArticleGroup testArticleGroup = articleGroupList.get(articleGroupList.size() - 1);
        assertThat(testArticleGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticleGroup.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticleGroup.getMainContent()).isEqualTo(UPDATED_MAIN_CONTENT);
        assertThat(testArticleGroup.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testArticleGroup.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testArticleGroup.getCreateName()).isEqualTo(UPDATED_CREATE_NAME);
        assertThat(testArticleGroup.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testArticleGroup.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testArticleGroup.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testArticleGroup.getFileId()).isEqualTo(UPDATED_FILE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();
        articleGroup.setId(longCount.incrementAndGet());

        // Create the ArticleGroup
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();
        articleGroup.setId(longCount.incrementAndGet());

        // Create the ArticleGroup
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleGroup() throws Exception {
        int databaseSizeBeforeUpdate = articleGroupRepository.findAll().size();
        articleGroup.setId(longCount.incrementAndGet());

        // Create the ArticleGroup
        ArticleGroupDTO articleGroupDTO = articleGroupMapper.toDto(articleGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleGroup in the database
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleGroup() throws Exception {
        // Initialize the database
        articleGroupRepository.saveAndFlush(articleGroup);

        int databaseSizeBeforeDelete = articleGroupRepository.findAll().size();

        // Delete the articleGroup
        restArticleGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleGroup> articleGroupList = articleGroupRepository.findAll();
        assertThat(articleGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
