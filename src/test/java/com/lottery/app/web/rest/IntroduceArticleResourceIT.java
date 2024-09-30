package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.IntroduceArticle;
import com.lottery.app.repository.IntroduceArticleRepository;
import com.lottery.app.service.dto.IntroduceArticleDTO;
import com.lottery.app.service.mapper.IntroduceArticleMapper;
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
 * Integration tests for the {@link IntroduceArticleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntroduceArticleResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/introduce-articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntroduceArticleRepository introduceArticleRepository;

    @Autowired
    private IntroduceArticleMapper introduceArticleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntroduceArticleMockMvc;

    private IntroduceArticle introduceArticle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntroduceArticle createEntity(EntityManager em) {
        IntroduceArticle introduceArticle = new IntroduceArticle()
            .code(DEFAULT_CODE)
            .articleCode(DEFAULT_ARTICLE_CODE)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .fileId(DEFAULT_FILE_ID);
        return introduceArticle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntroduceArticle createUpdatedEntity(EntityManager em) {
        IntroduceArticle introduceArticle = new IntroduceArticle()
            .code(UPDATED_CODE)
            .articleCode(UPDATED_ARTICLE_CODE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .fileId(UPDATED_FILE_ID);
        return introduceArticle;
    }

    @BeforeEach
    public void initTest() {
        introduceArticle = createEntity(em);
    }

    @Test
    @Transactional
    void createIntroduceArticle() throws Exception {
        int databaseSizeBeforeCreate = introduceArticleRepository.findAll().size();
        // Create the IntroduceArticle
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);
        restIntroduceArticleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeCreate + 1);
        IntroduceArticle testIntroduceArticle = introduceArticleList.get(introduceArticleList.size() - 1);
        assertThat(testIntroduceArticle.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIntroduceArticle.getArticleCode()).isEqualTo(DEFAULT_ARTICLE_CODE);
        assertThat(testIntroduceArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIntroduceArticle.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testIntroduceArticle.getFileId()).isEqualTo(DEFAULT_FILE_ID);
    }

    @Test
    @Transactional
    void createIntroduceArticleWithExistingId() throws Exception {
        // Create the IntroduceArticle with an existing ID
        introduceArticle.setId(1L);
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);

        int databaseSizeBeforeCreate = introduceArticleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntroduceArticleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntroduceArticles() throws Exception {
        // Initialize the database
        introduceArticleRepository.saveAndFlush(introduceArticle);

        // Get all the introduceArticleList
        restIntroduceArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(introduceArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].articleCode").value(hasItem(DEFAULT_ARTICLE_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)));
    }

    @Test
    @Transactional
    void getIntroduceArticle() throws Exception {
        // Initialize the database
        introduceArticleRepository.saveAndFlush(introduceArticle);

        // Get the introduceArticle
        restIntroduceArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, introduceArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(introduceArticle.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.articleCode").value(DEFAULT_ARTICLE_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID));
    }

    @Test
    @Transactional
    void getNonExistingIntroduceArticle() throws Exception {
        // Get the introduceArticle
        restIntroduceArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntroduceArticle() throws Exception {
        // Initialize the database
        introduceArticleRepository.saveAndFlush(introduceArticle);

        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();

        // Update the introduceArticle
        IntroduceArticle updatedIntroduceArticle = introduceArticleRepository.findById(introduceArticle.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIntroduceArticle are not directly saved in db
        em.detach(updatedIntroduceArticle);
        updatedIntroduceArticle
            .code(UPDATED_CODE)
            .articleCode(UPDATED_ARTICLE_CODE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .fileId(UPDATED_FILE_ID);
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(updatedIntroduceArticle);

        restIntroduceArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, introduceArticleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isOk());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
        IntroduceArticle testIntroduceArticle = introduceArticleList.get(introduceArticleList.size() - 1);
        assertThat(testIntroduceArticle.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIntroduceArticle.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testIntroduceArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIntroduceArticle.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testIntroduceArticle.getFileId()).isEqualTo(UPDATED_FILE_ID);
    }

    @Test
    @Transactional
    void putNonExistingIntroduceArticle() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();
        introduceArticle.setId(longCount.incrementAndGet());

        // Create the IntroduceArticle
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntroduceArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, introduceArticleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntroduceArticle() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();
        introduceArticle.setId(longCount.incrementAndGet());

        // Create the IntroduceArticle
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntroduceArticle() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();
        introduceArticle.setId(longCount.incrementAndGet());

        // Create the IntroduceArticle
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntroduceArticleWithPatch() throws Exception {
        // Initialize the database
        introduceArticleRepository.saveAndFlush(introduceArticle);

        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();

        // Update the introduceArticle using partial update
        IntroduceArticle partialUpdatedIntroduceArticle = new IntroduceArticle();
        partialUpdatedIntroduceArticle.setId(introduceArticle.getId());

        partialUpdatedIntroduceArticle.articleCode(UPDATED_ARTICLE_CODE).fileId(UPDATED_FILE_ID);

        restIntroduceArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntroduceArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntroduceArticle))
            )
            .andExpect(status().isOk());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
        IntroduceArticle testIntroduceArticle = introduceArticleList.get(introduceArticleList.size() - 1);
        assertThat(testIntroduceArticle.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIntroduceArticle.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testIntroduceArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIntroduceArticle.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testIntroduceArticle.getFileId()).isEqualTo(UPDATED_FILE_ID);
    }

    @Test
    @Transactional
    void fullUpdateIntroduceArticleWithPatch() throws Exception {
        // Initialize the database
        introduceArticleRepository.saveAndFlush(introduceArticle);

        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();

        // Update the introduceArticle using partial update
        IntroduceArticle partialUpdatedIntroduceArticle = new IntroduceArticle();
        partialUpdatedIntroduceArticle.setId(introduceArticle.getId());

        partialUpdatedIntroduceArticle
            .code(UPDATED_CODE)
            .articleCode(UPDATED_ARTICLE_CODE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .fileId(UPDATED_FILE_ID);

        restIntroduceArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntroduceArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntroduceArticle))
            )
            .andExpect(status().isOk());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
        IntroduceArticle testIntroduceArticle = introduceArticleList.get(introduceArticleList.size() - 1);
        assertThat(testIntroduceArticle.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIntroduceArticle.getArticleCode()).isEqualTo(UPDATED_ARTICLE_CODE);
        assertThat(testIntroduceArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIntroduceArticle.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testIntroduceArticle.getFileId()).isEqualTo(UPDATED_FILE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIntroduceArticle() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();
        introduceArticle.setId(longCount.incrementAndGet());

        // Create the IntroduceArticle
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntroduceArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, introduceArticleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntroduceArticle() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();
        introduceArticle.setId(longCount.incrementAndGet());

        // Create the IntroduceArticle
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntroduceArticle() throws Exception {
        int databaseSizeBeforeUpdate = introduceArticleRepository.findAll().size();
        introduceArticle.setId(longCount.incrementAndGet());

        // Create the IntroduceArticle
        IntroduceArticleDTO introduceArticleDTO = introduceArticleMapper.toDto(introduceArticle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntroduceArticleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(introduceArticleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntroduceArticle in the database
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntroduceArticle() throws Exception {
        // Initialize the database
        introduceArticleRepository.saveAndFlush(introduceArticle);

        int databaseSizeBeforeDelete = introduceArticleRepository.findAll().size();

        // Delete the introduceArticle
        restIntroduceArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, introduceArticle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntroduceArticle> introduceArticleList = introduceArticleRepository.findAll();
        assertThat(introduceArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
