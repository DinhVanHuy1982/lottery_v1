package com.lottery.app.service.impl;

import com.lottery.app.domain.LevelDepositsResult;
import com.lottery.app.repository.LevelDepositsResultRepository;
import com.lottery.app.service.LevelDepositsResultService;
import com.lottery.app.service.dto.LevelDepositsResultDTO;
import com.lottery.app.service.mapper.LevelDepositsResultMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.LevelDepositsResult}.
 */
@Service
@Transactional
public class LevelDepositsResultServiceImpl implements LevelDepositsResultService {

    private final Logger log = LoggerFactory.getLogger(LevelDepositsResultServiceImpl.class);

    private final LevelDepositsResultRepository levelDepositsResultRepository;

    private final LevelDepositsResultMapper levelDepositsResultMapper;

    public LevelDepositsResultServiceImpl(
        LevelDepositsResultRepository levelDepositsResultRepository,
        LevelDepositsResultMapper levelDepositsResultMapper
    ) {
        this.levelDepositsResultRepository = levelDepositsResultRepository;
        this.levelDepositsResultMapper = levelDepositsResultMapper;
    }

    @Override
    public LevelDepositsResultDTO save(LevelDepositsResultDTO levelDepositsResultDTO) {
        log.debug("Request to save LevelDepositsResult : {}", levelDepositsResultDTO);
        LevelDepositsResult levelDepositsResult = levelDepositsResultMapper.toEntity(levelDepositsResultDTO);
        levelDepositsResult = levelDepositsResultRepository.save(levelDepositsResult);
        return levelDepositsResultMapper.toDto(levelDepositsResult);
    }

    @Override
    public LevelDepositsResultDTO update(LevelDepositsResultDTO levelDepositsResultDTO) {
        log.debug("Request to update LevelDepositsResult : {}", levelDepositsResultDTO);
        LevelDepositsResult levelDepositsResult = levelDepositsResultMapper.toEntity(levelDepositsResultDTO);
        levelDepositsResult = levelDepositsResultRepository.save(levelDepositsResult);
        return levelDepositsResultMapper.toDto(levelDepositsResult);
    }

    @Override
    public Optional<LevelDepositsResultDTO> partialUpdate(LevelDepositsResultDTO levelDepositsResultDTO) {
        log.debug("Request to partially update LevelDepositsResult : {}", levelDepositsResultDTO);

        return levelDepositsResultRepository
            .findById(levelDepositsResultDTO.getId())
            .map(existingLevelDepositsResult -> {
                levelDepositsResultMapper.partialUpdate(existingLevelDepositsResult, levelDepositsResultDTO);

                return existingLevelDepositsResult;
            })
            .map(levelDepositsResultRepository::save)
            .map(levelDepositsResultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelDepositsResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LevelDepositsResults");
        return levelDepositsResultRepository.findAll(pageable).map(levelDepositsResultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelDepositsResultDTO> findOne(Long id) {
        log.debug("Request to get LevelDepositsResult : {}", id);
        return levelDepositsResultRepository.findById(id).map(levelDepositsResultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelDepositsResult : {}", id);
        levelDepositsResultRepository.deleteById(id);
    }
}
