package com.lottery.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lottery.app.IntegrationTest;
import com.lottery.app.domain.Articles;
import com.lottery.app.repository.ArticlesRepository;
import com.lottery.app.service.dto.ArticlesDTO;
import com.lottery.app.service.mapper.ArticlesMapper;
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
 * Integration tests for the {@link ArticlesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticlesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_CHOICE = 1L;
    private static final Long UPDATED_NUMBER_CHOICE = 2L;

    private static final Long DEFAULT_NUMBER_OF_DIGITS = 1L;
    private static final Long UPDATED_NUMBER_OF_DIGITS = 2L;

    private static final String DEFAULT_TIME_START = "AAAAAAAAAA";
    private static final String UPDATED_TIME_START = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_END = "AAAAAAAAAA";
    private static final String UPDATED_TIME_END = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticlesMockMvc;

    private Articles articles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articles createEntity(EntityManager em) {
        Articles articles = new Articles()
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .fileId(DEFAULT_FILE_ID)
            .updateName(DEFAULT_UPDATE_NAME)
            .numberChoice(DEFAULT_NUMBER_CHOICE)
            .numberOfDigits(DEFAULT_NUMBER_OF_DIGITS)
            .timeStart(DEFAULT_TIME_START)
            .timeEnd(DEFAULT_TIME_END);
        return articles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articles createUpdatedEntity(EntityManager em) {
        Articles articles = new Articles()
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .fileId(UPDATED_FILE_ID)
            .updateName(UPDATED_UPDATE_NAME)
            .numberChoice(UPDATED_NUMBER_CHOICE)
            .numberOfDigits(UPDATED_NUMBER_OF_DIGITS)
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END);
        return articles;
    }

    @BeforeEach
    public void initTest() {
        articles = createEntity(em);
    }

    @Test
    @Transactional
    void createArticles() throws Exception {
        int databaseSizeBeforeCreate = articlesRepository.findAll().size();
        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);
        restArticlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articlesDTO)))
            .andExpect(status().isCreated());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeCreate + 1);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testArticles.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testArticles.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArticles.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testArticles.getUpdateName()).isEqualTo(DEFAULT_UPDATE_NAME);
        assertThat(testArticles.getNumberChoice()).isEqualTo(DEFAULT_NUMBER_CHOICE);
        assertThat(testArticles.getNumberOfDigits()).isEqualTo(DEFAULT_NUMBER_OF_DIGITS);
        assertThat(testArticles.getTimeStart()).isEqualTo(DEFAULT_TIME_START);
        assertThat(testArticles.getTimeEnd()).isEqualTo(DEFAULT_TIME_END);
    }

    @Test
    @Transactional
    void createArticlesWithExistingId() throws Exception {
        // Create the Articles with an existing ID
        articles.setId(1L);
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        int databaseSizeBeforeCreate = articlesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articlesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = articlesRepository.findAll().size();
        // set the field null
        articles.setCode(null);

        // Create the Articles, which fails.
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        restArticlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articlesDTO)))
            .andExpect(status().isBadRequest());

        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        // Get all the articlesList
        restArticlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articles.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].updateName").value(hasItem(DEFAULT_UPDATE_NAME)))
            .andExpect(jsonPath("$.[*].numberChoice").value(hasItem(DEFAULT_NUMBER_CHOICE.intValue())))
            .andExpect(jsonPath("$.[*].numberOfDigits").value(hasItem(DEFAULT_NUMBER_OF_DIGITS.intValue())))
            .andExpect(jsonPath("$.[*].timeStart").value(hasItem(DEFAULT_TIME_START)))
            .andExpect(jsonPath("$.[*].timeEnd").value(hasItem(DEFAULT_TIME_END)));
    }

    @Test
    @Transactional
    void getArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        // Get the articles
        restArticlesMockMvc
            .perform(get(ENTITY_API_URL_ID, articles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articles.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.updateName").value(DEFAULT_UPDATE_NAME))
            .andExpect(jsonPath("$.numberChoice").value(DEFAULT_NUMBER_CHOICE.intValue()))
            .andExpect(jsonPath("$.numberOfDigits").value(DEFAULT_NUMBER_OF_DIGITS.intValue()))
            .andExpect(jsonPath("$.timeStart").value(DEFAULT_TIME_START))
            .andExpect(jsonPath("$.timeEnd").value(DEFAULT_TIME_END));
    }

    @Test
    @Transactional
    void getNonExistingArticles() throws Exception {
        // Get the articles
        restArticlesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Update the articles
        Articles updatedArticles = articlesRepository.findById(articles.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArticles are not directly saved in db
        em.detach(updatedArticles);
        updatedArticles
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .fileId(UPDATED_FILE_ID)
            .updateName(UPDATED_UPDATE_NAME)
            .numberChoice(UPDATED_NUMBER_CHOICE)
            .numberOfDigits(UPDATED_NUMBER_OF_DIGITS)
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END);
        ArticlesDTO articlesDTO = articlesMapper.toDto(updatedArticles);

        restArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticles.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticles.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArticles.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testArticles.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testArticles.getNumberChoice()).isEqualTo(UPDATED_NUMBER_CHOICE);
        assertThat(testArticles.getNumberOfDigits()).isEqualTo(UPDATED_NUMBER_OF_DIGITS);
        assertThat(testArticles.getTimeStart()).isEqualTo(UPDATED_TIME_START);
        assertThat(testArticles.getTimeEnd()).isEqualTo(UPDATED_TIME_END);
    }

    @Test
    @Transactional
    void putNonExistingArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(longCount.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(longCount.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(longCount.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articlesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticlesWithPatch() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Update the articles using partial update
        Articles partialUpdatedArticles = new Articles();
        partialUpdatedArticles.setId(articles.getId());

        partialUpdatedArticles
            .title(UPDATED_TITLE)
            .numberChoice(UPDATED_NUMBER_CHOICE)
            .numberOfDigits(UPDATED_NUMBER_OF_DIGITS)
            .timeStart(UPDATED_TIME_START);

        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticles))
            )
            .andExpect(status().isOk());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testArticles.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticles.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArticles.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testArticles.getUpdateName()).isEqualTo(DEFAULT_UPDATE_NAME);
        assertThat(testArticles.getNumberChoice()).isEqualTo(UPDATED_NUMBER_CHOICE);
        assertThat(testArticles.getNumberOfDigits()).isEqualTo(UPDATED_NUMBER_OF_DIGITS);
        assertThat(testArticles.getTimeStart()).isEqualTo(UPDATED_TIME_START);
        assertThat(testArticles.getTimeEnd()).isEqualTo(DEFAULT_TIME_END);
    }

    @Test
    @Transactional
    void fullUpdateArticlesWithPatch() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Update the articles using partial update
        Articles partialUpdatedArticles = new Articles();
        partialUpdatedArticles.setId(articles.getId());

        partialUpdatedArticles
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .fileId(UPDATED_FILE_ID)
            .updateName(UPDATED_UPDATE_NAME)
            .numberChoice(UPDATED_NUMBER_CHOICE)
            .numberOfDigits(UPDATED_NUMBER_OF_DIGITS)
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END);

        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticles))
            )
            .andExpect(status().isOk());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticles.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticles.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArticles.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testArticles.getUpdateName()).isEqualTo(UPDATED_UPDATE_NAME);
        assertThat(testArticles.getNumberChoice()).isEqualTo(UPDATED_NUMBER_CHOICE);
        assertThat(testArticles.getNumberOfDigits()).isEqualTo(UPDATED_NUMBER_OF_DIGITS);
        assertThat(testArticles.getTimeStart()).isEqualTo(UPDATED_TIME_START);
        assertThat(testArticles.getTimeEnd()).isEqualTo(UPDATED_TIME_END);
    }

    @Test
    @Transactional
    void patchNonExistingArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(longCount.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articlesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(longCount.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(longCount.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeDelete = articlesRepository.findAll().size();

        // Delete the articles
        restArticlesMockMvc
            .perform(delete(ENTITY_API_URL_ID, articles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
