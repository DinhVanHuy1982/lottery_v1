package com.lottery.app.service.impl;

import com.lottery.app.config.Constants;
import com.lottery.app.config.ServiceResult;
import com.lottery.app.domain.ArticleGroup;
import com.lottery.app.repository.ArticleGroupRepository;
import com.lottery.app.service.ArticleGroupService;
import com.lottery.app.service.dto.ArticleGroupDTO;
import com.lottery.app.service.mapper.ArticleGroupMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.ArticleGroup}.
 */
@Service
@Transactional
public class ArticleGroupServiceImpl implements ArticleGroupService {

    private final Logger log = LoggerFactory.getLogger(ArticleGroupServiceImpl.class);

    private final ArticleGroupRepository articleGroupRepository;

    private final ArticleGroupMapper articleGroupMapper;

    public ArticleGroupServiceImpl(ArticleGroupRepository articleGroupRepository, ArticleGroupMapper articleGroupMapper) {
        this.articleGroupRepository = articleGroupRepository;
        this.articleGroupMapper = articleGroupMapper;
    }

    @Override
    public ArticleGroupDTO save(ArticleGroupDTO articleGroupDTO) {
        log.debug("Request to save ArticleGroup : {}", articleGroupDTO);
        ArticleGroup articleGroup = articleGroupMapper.toEntity(articleGroupDTO);
        articleGroup = articleGroupRepository.save(articleGroup);
        return articleGroupMapper.toDto(articleGroup);
    }

    @Override
    public ArticleGroupDTO update(ArticleGroupDTO articleGroupDTO) {
        log.debug("Request to update ArticleGroup : {}", articleGroupDTO);
        ArticleGroup articleGroup = articleGroupMapper.toEntity(articleGroupDTO);
        articleGroup = articleGroupRepository.save(articleGroup);
        return articleGroupMapper.toDto(articleGroup);
    }

    @Override
    public Optional<ArticleGroupDTO> partialUpdate(ArticleGroupDTO articleGroupDTO) {
        log.debug("Request to partially update ArticleGroup : {}", articleGroupDTO);

        return articleGroupRepository
            .findById(articleGroupDTO.getId())
            .map(existingArticleGroup -> {
                articleGroupMapper.partialUpdate(existingArticleGroup, articleGroupDTO);

                return existingArticleGroup;
            })
            .map(articleGroupRepository::save)
            .map(articleGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleGroups");
        return articleGroupRepository.findAll(pageable).map(articleGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleGroupDTO> findOne(Long id) {
        log.debug("Request to get ArticleGroup : {}", id);
        return articleGroupRepository.findById(id).map(articleGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticleGroup : {}", id);
        articleGroupRepository.deleteById(id);
    }

    @Override
    public ServiceResult<List<Map<String, Object>>> getLstArticleGroupCodeName() {
        List<Map<String, Object>> lstMap = this.articleGroupRepository.getLstArticleGroupCodeAndName();
        return new ServiceResult<>(lstMap, Constants.CODE_STATUS.SUCCESS, "");
    }
}
