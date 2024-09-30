package com.lottery.app.service.impl;

import com.lottery.app.domain.Articles;
import com.lottery.app.repository.ArticlesRepository;
import com.lottery.app.service.ArticlesService;
import com.lottery.app.service.dto.ArticlesDTO;
import com.lottery.app.service.mapper.ArticlesMapper;
import java.util.Optional;
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

    private final ArticlesMapper articlesMapper;

    public ArticlesServiceImpl(ArticlesRepository articlesRepository, ArticlesMapper articlesMapper) {
        this.articlesRepository = articlesRepository;
        this.articlesMapper = articlesMapper;
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
}
