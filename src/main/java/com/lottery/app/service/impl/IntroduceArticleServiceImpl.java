package com.lottery.app.service.impl;

import com.lottery.app.domain.IntroduceArticle;
import com.lottery.app.repository.IntroduceArticleRepository;
import com.lottery.app.service.IntroduceArticleService;
import com.lottery.app.service.dto.IntroduceArticleDTO;
import com.lottery.app.service.mapper.IntroduceArticleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.IntroduceArticle}.
 */
@Service
@Transactional
public class IntroduceArticleServiceImpl implements IntroduceArticleService {

    private final Logger log = LoggerFactory.getLogger(IntroduceArticleServiceImpl.class);

    private final IntroduceArticleRepository introduceArticleRepository;

    private final IntroduceArticleMapper introduceArticleMapper;

    public IntroduceArticleServiceImpl(
        IntroduceArticleRepository introduceArticleRepository,
        IntroduceArticleMapper introduceArticleMapper
    ) {
        this.introduceArticleRepository = introduceArticleRepository;
        this.introduceArticleMapper = introduceArticleMapper;
    }

    @Override
    public IntroduceArticleDTO save(IntroduceArticleDTO introduceArticleDTO) {
        log.debug("Request to save IntroduceArticle : {}", introduceArticleDTO);
        IntroduceArticle introduceArticle = introduceArticleMapper.toEntity(introduceArticleDTO);
        introduceArticle = introduceArticleRepository.save(introduceArticle);
        return introduceArticleMapper.toDto(introduceArticle);
    }

    @Override
    public IntroduceArticleDTO update(IntroduceArticleDTO introduceArticleDTO) {
        log.debug("Request to update IntroduceArticle : {}", introduceArticleDTO);
        IntroduceArticle introduceArticle = introduceArticleMapper.toEntity(introduceArticleDTO);
        introduceArticle = introduceArticleRepository.save(introduceArticle);
        return introduceArticleMapper.toDto(introduceArticle);
    }

    @Override
    public Optional<IntroduceArticleDTO> partialUpdate(IntroduceArticleDTO introduceArticleDTO) {
        log.debug("Request to partially update IntroduceArticle : {}", introduceArticleDTO);

        return introduceArticleRepository
            .findById(introduceArticleDTO.getId())
            .map(existingIntroduceArticle -> {
                introduceArticleMapper.partialUpdate(existingIntroduceArticle, introduceArticleDTO);

                return existingIntroduceArticle;
            })
            .map(introduceArticleRepository::save)
            .map(introduceArticleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntroduceArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntroduceArticles");
        return introduceArticleRepository.findAll(pageable).map(introduceArticleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntroduceArticleDTO> findOne(Long id) {
        log.debug("Request to get IntroduceArticle : {}", id);
        return introduceArticleRepository.findById(id).map(introduceArticleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntroduceArticle : {}", id);
        introduceArticleRepository.deleteById(id);
    }
}
