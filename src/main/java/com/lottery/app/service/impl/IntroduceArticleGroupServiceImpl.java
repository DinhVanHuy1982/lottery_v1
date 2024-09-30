package com.lottery.app.service.impl;

import com.lottery.app.domain.IntroduceArticleGroup;
import com.lottery.app.repository.IntroduceArticleGroupRepository;
import com.lottery.app.service.IntroduceArticleGroupService;
import com.lottery.app.service.dto.IntroduceArticleGroupDTO;
import com.lottery.app.service.mapper.IntroduceArticleGroupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.IntroduceArticleGroup}.
 */
@Service
@Transactional
public class IntroduceArticleGroupServiceImpl implements IntroduceArticleGroupService {

    private final Logger log = LoggerFactory.getLogger(IntroduceArticleGroupServiceImpl.class);

    private final IntroduceArticleGroupRepository introduceArticleGroupRepository;

    private final IntroduceArticleGroupMapper introduceArticleGroupMapper;

    public IntroduceArticleGroupServiceImpl(
        IntroduceArticleGroupRepository introduceArticleGroupRepository,
        IntroduceArticleGroupMapper introduceArticleGroupMapper
    ) {
        this.introduceArticleGroupRepository = introduceArticleGroupRepository;
        this.introduceArticleGroupMapper = introduceArticleGroupMapper;
    }

    @Override
    public IntroduceArticleGroupDTO save(IntroduceArticleGroupDTO introduceArticleGroupDTO) {
        log.debug("Request to save IntroduceArticleGroup : {}", introduceArticleGroupDTO);
        IntroduceArticleGroup introduceArticleGroup = introduceArticleGroupMapper.toEntity(introduceArticleGroupDTO);
        introduceArticleGroup = introduceArticleGroupRepository.save(introduceArticleGroup);
        return introduceArticleGroupMapper.toDto(introduceArticleGroup);
    }

    @Override
    public IntroduceArticleGroupDTO update(IntroduceArticleGroupDTO introduceArticleGroupDTO) {
        log.debug("Request to update IntroduceArticleGroup : {}", introduceArticleGroupDTO);
        IntroduceArticleGroup introduceArticleGroup = introduceArticleGroupMapper.toEntity(introduceArticleGroupDTO);
        introduceArticleGroup = introduceArticleGroupRepository.save(introduceArticleGroup);
        return introduceArticleGroupMapper.toDto(introduceArticleGroup);
    }

    @Override
    public Optional<IntroduceArticleGroupDTO> partialUpdate(IntroduceArticleGroupDTO introduceArticleGroupDTO) {
        log.debug("Request to partially update IntroduceArticleGroup : {}", introduceArticleGroupDTO);

        return introduceArticleGroupRepository
            .findById(introduceArticleGroupDTO.getId())
            .map(existingIntroduceArticleGroup -> {
                introduceArticleGroupMapper.partialUpdate(existingIntroduceArticleGroup, introduceArticleGroupDTO);

                return existingIntroduceArticleGroup;
            })
            .map(introduceArticleGroupRepository::save)
            .map(introduceArticleGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntroduceArticleGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntroduceArticleGroups");
        return introduceArticleGroupRepository.findAll(pageable).map(introduceArticleGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntroduceArticleGroupDTO> findOne(Long id) {
        log.debug("Request to get IntroduceArticleGroup : {}", id);
        return introduceArticleGroupRepository.findById(id).map(introduceArticleGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntroduceArticleGroup : {}", id);
        introduceArticleGroupRepository.deleteById(id);
    }
}
