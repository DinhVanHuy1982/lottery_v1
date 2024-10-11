package com.lottery.app.service.impl;

import com.lottery.app.domain.LevelDeposits;
import com.lottery.app.repository.LevelDepositsRepository;
import com.lottery.app.service.LevelDepositsService;
import com.lottery.app.service.dto.LevelDepositsDTO;
import com.lottery.app.service.mapper.LevelDepositsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.LevelDeposits}.
 */
@Service
@Transactional
public class LevelDepositsServiceImpl implements LevelDepositsService {

    private final Logger log = LoggerFactory.getLogger(LevelDepositsServiceImpl.class);

    private final LevelDepositsRepository levelDepositsRepository;

    private final LevelDepositsMapper levelDepositsMapper;

    public LevelDepositsServiceImpl(LevelDepositsRepository levelDepositsRepository, LevelDepositsMapper levelDepositsMapper) {
        this.levelDepositsRepository = levelDepositsRepository;
        this.levelDepositsMapper = levelDepositsMapper;
    }

    @Override
    public LevelDepositsDTO save(LevelDepositsDTO levelDepositsDTO) {
        log.debug("Request to save LevelDeposits : {}", levelDepositsDTO);
        LevelDeposits levelDeposits = levelDepositsMapper.toEntity(levelDepositsDTO);
        levelDeposits = levelDepositsRepository.save(levelDeposits);
        return levelDepositsMapper.toDto(levelDeposits);
    }

    @Override
    public LevelDepositsDTO update(LevelDepositsDTO levelDepositsDTO) {
        log.debug("Request to update LevelDeposits : {}", levelDepositsDTO);
        LevelDeposits levelDeposits = levelDepositsMapper.toEntity(levelDepositsDTO);
        levelDeposits = levelDepositsRepository.save(levelDeposits);
        return levelDepositsMapper.toDto(levelDeposits);
    }

    @Override
    public Optional<LevelDepositsDTO> partialUpdate(LevelDepositsDTO levelDepositsDTO) {
        log.debug("Request to partially update LevelDeposits : {}", levelDepositsDTO);

        return levelDepositsRepository
            .findById(levelDepositsDTO.getId())
            .map(existingLevelDeposits -> {
                levelDepositsMapper.partialUpdate(existingLevelDeposits, levelDepositsDTO);

                return existingLevelDeposits;
            })
            .map(levelDepositsRepository::save)
            .map(levelDepositsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LevelDepositsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LevelDeposits");
        return levelDepositsRepository.findAll(pageable).map(levelDepositsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LevelDepositsDTO> findOne(Long id) {
        log.debug("Request to get LevelDeposits : {}", id);
        return levelDepositsRepository.findById(id).map(levelDepositsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelDeposits : {}", id);
        levelDepositsRepository.deleteById(id);
    }
}
