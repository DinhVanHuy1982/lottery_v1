package com.lottery.app.service.impl;

import com.lottery.app.domain.RandomResults;
import com.lottery.app.repository.RandomResultsRepository;
import com.lottery.app.service.RandomResultsService;
import com.lottery.app.service.dto.RandomResultsDTO;
import com.lottery.app.service.mapper.RandomResultsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.RandomResults}.
 */
@Service
@Transactional
public class RandomResultsServiceImpl implements RandomResultsService {

    private final Logger log = LoggerFactory.getLogger(RandomResultsServiceImpl.class);

    private final RandomResultsRepository randomResultsRepository;

    private final RandomResultsMapper randomResultsMapper;

    public RandomResultsServiceImpl(RandomResultsRepository randomResultsRepository, RandomResultsMapper randomResultsMapper) {
        this.randomResultsRepository = randomResultsRepository;
        this.randomResultsMapper = randomResultsMapper;
    }

    @Override
    public RandomResultsDTO save(RandomResultsDTO randomResultsDTO) {
        log.debug("Request to save RandomResults : {}", randomResultsDTO);
        RandomResults randomResults = randomResultsMapper.toEntity(randomResultsDTO);
        randomResults = randomResultsRepository.save(randomResults);
        return randomResultsMapper.toDto(randomResults);
    }

    @Override
    public RandomResultsDTO update(RandomResultsDTO randomResultsDTO) {
        log.debug("Request to update RandomResults : {}", randomResultsDTO);
        RandomResults randomResults = randomResultsMapper.toEntity(randomResultsDTO);
        randomResults = randomResultsRepository.save(randomResults);
        return randomResultsMapper.toDto(randomResults);
    }

    @Override
    public Optional<RandomResultsDTO> partialUpdate(RandomResultsDTO randomResultsDTO) {
        log.debug("Request to partially update RandomResults : {}", randomResultsDTO);

        return randomResultsRepository
            .findById(randomResultsDTO.getId())
            .map(existingRandomResults -> {
                randomResultsMapper.partialUpdate(existingRandomResults, randomResultsDTO);

                return existingRandomResults;
            })
            .map(randomResultsRepository::save)
            .map(randomResultsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RandomResultsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RandomResults");
        return randomResultsRepository.findAll(pageable).map(randomResultsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RandomResultsDTO> findOne(Long id) {
        log.debug("Request to get RandomResults : {}", id);
        return randomResultsRepository.findById(id).map(randomResultsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RandomResults : {}", id);
        randomResultsRepository.deleteById(id);
    }
}
