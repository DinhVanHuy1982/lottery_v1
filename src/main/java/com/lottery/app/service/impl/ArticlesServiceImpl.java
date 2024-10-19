package com.lottery.app.service.impl;

import com.lottery.app.commons.Translator;
import com.lottery.app.commons.ValidateUtils;
import com.lottery.app.config.Constants;
import com.lottery.app.config.ServiceResult;
import com.lottery.app.domain.Articles;
import com.lottery.app.domain.IntroduceArticle;
import com.lottery.app.domain.LevelDeposits;
import com.lottery.app.domain.Prizes;
import com.lottery.app.repository.ArticlesRepository;
import com.lottery.app.repository.IntroduceArticleRepository;
import com.lottery.app.repository.LevelDepositsRepository;
import com.lottery.app.repository.PrizesRepository;
import com.lottery.app.repository.custome.ArticlesCustomeRepository;
import com.lottery.app.security.SecurityUtils;
import com.lottery.app.service.ArticlesService;
import com.lottery.app.service.dto.ArticlesDTO;
import com.lottery.app.service.dto.SearchDTO;
import com.lottery.app.service.dto.body.search.ArticlesSearchDTO;
import com.lottery.app.service.mapper.ArticlesMapper;
import com.lottery.app.service.mapper.IntroduceArticleMapper;
import com.lottery.app.service.mapper.LevelDepositsMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.Articles}.
 */
@Service
@Transactional
public class ArticlesServiceImpl implements ArticlesService {

    private final Logger log = LoggerFactory.getLogger(ArticlesServiceImpl.class);

    private final ArticlesRepository articlesRepository;
    private final ArticlesCustomeRepository articlesCustomeRepository;

    private final ArticlesMapper articlesMapper;

    private final LevelDepositsMapper levelDepositsMapper;
    private final IntroduceArticleMapper introduceArticleMapper;
    private final IntroduceArticleRepository introduceArticleRepository;
    private final LevelDepositsRepository levelDepositsRepository;
    private final PrizesRepository prizesRepository;

    public ArticlesServiceImpl(
        ArticlesRepository articlesRepository,
        ArticlesCustomeRepository articlesCustomeRepository,
        ArticlesMapper articlesMapper,
        LevelDepositsMapper levelDepositsMapper,
        IntroduceArticleMapper introduceArticleMapper,
        IntroduceArticleRepository introduceArticleRepository,
        LevelDepositsRepository levelDepositsRepository,
        PrizesRepository prizesRepository
    ) {
        this.articlesRepository = articlesRepository;
        this.articlesCustomeRepository = articlesCustomeRepository;
        this.articlesMapper = articlesMapper;
        this.levelDepositsMapper = levelDepositsMapper;
        this.introduceArticleMapper = introduceArticleMapper;
        this.introduceArticleRepository = introduceArticleRepository;
        this.levelDepositsRepository = levelDepositsRepository;
        this.prizesRepository = prizesRepository;
    }

    @Override
    public ArticlesDTO save(ArticlesDTO articlesDTO) {
        log.debug("Request to save Articles : {}", articlesDTO);
        Articles articles = articlesMapper.toEntity(articlesDTO);
        articles = articlesRepository.save(articles);
        return articlesMapper.toDto(articles);
    }

    @Override
    public ArticlesDTO update(ArticlesDTO articlesDTO) {
        log.debug("Request to update Articles : {}", articlesDTO);
        Articles articles = articlesMapper.toEntity(articlesDTO);
        articles = articlesRepository.save(articles);
        return articlesMapper.toDto(articles);
    }

    @Override
    public Optional<ArticlesDTO> partialUpdate(ArticlesDTO articlesDTO) {
        log.debug("Request to partially update Articles : {}", articlesDTO);

        return articlesRepository
            .findById(articlesDTO.getId())
            .map(existingArticles -> {
                articlesMapper.partialUpdate(existingArticles, articlesDTO);

                return existingArticles;
            })
            .map(articlesRepository::save)
            .map(articlesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticlesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articlesRepository.findAll(pageable).map(articlesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticlesDTO> findOne(Long id) {
        log.debug("Request to get Articles : {}", id);
        return articlesRepository.findById(id).map(articlesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Articles : {}", id);
        articlesRepository.deleteById(id);
    }

    @Override
    public ServiceResult<String> createUpdateAriticle(ArticlesDTO articlesDTO) {
        ServiceResult<String> validate = validateArticle(articlesDTO);
        if (validate.getStatus().equals(Constants.CODE_STATUS.ERROR)) {
            return new ServiceResult<>(validate.getData(), Constants.CODE_STATUS.ERROR, validate.getData());
        }

        String code = UUID.randomUUID().toString() + System.currentTimeMillis();

        if (articlesDTO.getAction().equals("create")) {
            articlesDTO.setCode(code);
        }

        Articles articles = this.articlesMapper.toEntity(articlesDTO);

        Optional<String> userName = SecurityUtils.getCurrentUserLogin();
        Instant now = Instant.now();

        List<LevelDeposits> lstDeposits = this.levelDepositsMapper.toEntity(articlesDTO.getLevelDeposits());
        lstDeposits.forEach(item -> {
            item.setArticleCode(articlesDTO.getCode());
            item.setUpdateName(userName.orElse(null));
            item.setUpdateTime(now);
            item.setCode(UUID.randomUUID().toString() + System.currentTimeMillis());
        });

        List<IntroduceArticle> lstIntroduceArticle = new ArrayList<>();
        if (articlesDTO.getLstIntroduce() != null) {
            lstIntroduceArticle = this.introduceArticleMapper.toEntity(articlesDTO.getLstIntroduce());
            lstIntroduceArticle.forEach(item -> {
                item.setArticleCode(articlesDTO.getCode());
                item.setCode(UUID.randomUUID().toString() + System.currentTimeMillis());
                // thiếu lưu file
            });
        }

        List<Prizes> lstPrizes = new ArrayList<>();
        for (int i = 0; i < articlesDTO.getNumberPrize(); i++) {
            Prizes prizes = new Prizes();
            prizes.setCode(UUID.randomUUID().toString() + System.currentTimeMillis());
            prizes.setLevelCup("G" + i);
            prizes.setArticleCode(articlesDTO.getCode());
            prizes.setNumberPrize(1L);
            prizes.setCreateTime(Instant.now());
            prizes.setCreateName(userName.orElse(null));
            lstPrizes.add(prizes);
        }

        if (articlesDTO.getAction().equals("update")) {
            this.levelDepositsRepository.deleteByArticleCode(articles.getCode());
            this.introduceArticleRepository.deleteByArticleCode(articles.getCode());
            this.prizesRepository.deleteByArticleCode(articles.getCode());
        }

        this.levelDepositsRepository.saveAll(lstDeposits);
        this.introduceArticleRepository.saveAll(lstIntroduceArticle);
        this.prizesRepository.saveAll(lstPrizes);
        this.articlesRepository.save(articles);

        return new ServiceResult<>(
            Translator.toLocale("success.create"),
            Constants.CODE_STATUS.SUCCESS,
            Translator.toLocale("success.create")
        );
    }

    @Override
    public ServiceResult<Page<ArticlesDTO>> searchArticles(SearchDTO<ArticlesSearchDTO> searchDTO) {
        Page<ArticlesDTO> page = this.articlesCustomeRepository.searchArticles(searchDTO);
        return new ServiceResult<>(page, Constants.CODE_STATUS.SUCCESS, "");
    }

    ServiceResult<String> validateArticle(ArticlesDTO articlesDTO) {
        ServiceResult<String> serviceResult = new ServiceResult<>();
        String err = "";
        List<String> listArticleName = this.articlesRepository.getLstArticleName(articlesDTO.getId());

        // validate name
        if (ValidateUtils.stringEmpty(articlesDTO.getName())) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.name"));
        } else if (articlesDTO.getName().length() > 100) {
            err = Translator.toLocale("error.maxlenngth", Translator.toLocale("article.name", "100"));
        } else if (listArticleName.contains(articlesDTO.getName())) {
            err = Translator.toLocale("error.exits", Translator.toLocale("article.name"));
        }

        // validate title
        if (ValidateUtils.stringEmpty(articlesDTO.getTitle())) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.name"));
        } else if (articlesDTO.getTitle().length() > 500) {
            err = Translator.toLocale("error.maxlenngth", Translator.toLocale("article.name", "100"));
        }

        // validate articleGroup
        if (ValidateUtils.stringEmpty(articlesDTO.getArticleGroupCode())) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.articleGroup"));
        }

        // validate total prize
        if (articlesDTO.getNumberPrize() == null) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.total.prize"));
        } else if (articlesDTO.getNumberPrize() < 0) {
            err = Translator.toLocale("error.invalid", Translator.toLocale("article.total.prize"));
        }

        if (articlesDTO.getNumberChoice() == null) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.total.prize"));
        } else if (articlesDTO.getNumberChoice() < 0) {
            err = Translator.toLocale("error.invalid", Translator.toLocale("article.total.prize"));
        }

        if (articlesDTO.getNumberOfDigits() == null) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.number.choice"));
        } else if (articlesDTO.getNumberOfDigits() < 0) {
            err = Translator.toLocale("error.invalid", Translator.toLocale("article.of.digits"));
        }

        // validate time
        if (ValidateUtils.stringEmpty(articlesDTO.getTimeStart())) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.time.start"));
        }
        if (ValidateUtils.stringEmpty(articlesDTO.getTimeEnd())) {
            err = Translator.toLocale("error.empty", Translator.toLocale("article.time.end"));
        } // => thiếu validate tg kết thúc trước tg quay

        // validate content
        if (!ValidateUtils.stringEmpty(articlesDTO.getContent()) && articlesDTO.getContent().length() > 2000) {
            err = Translator.toLocale("error.maxlenngth", Translator.toLocale("article.content", "100"));
        }

        // validate deposits

        // validate introduce article

        serviceResult.setData(err);
        if (!err.isEmpty()) {
            serviceResult.setStatus(Constants.CODE_STATUS.ERROR);
        } else {
            serviceResult.setStatus(Constants.CODE_STATUS.SUCCESS);
        }
        return serviceResult;
    }
}
