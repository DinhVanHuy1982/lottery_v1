package com.lottery.app.service.impl;

import com.lottery.app.domain.Functions;
import com.lottery.app.repository.FunctionsRepository;
import com.lottery.app.service.FunctionsService;
import com.lottery.app.service.dto.FunctionsDTO;
import com.lottery.app.service.mapper.FunctionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.Functions}.
 */
@Service
@Transactional
public class FunctionsServiceImpl implements FunctionsService {

    private final Logger log = LoggerFactory.getLogger(FunctionsServiceImpl.class);

    private final FunctionsRepository functionsRepository;

    private final FunctionsMapper functionsMapper;

    public FunctionsServiceImpl(FunctionsRepository functionsRepository, FunctionsMapper functionsMapper) {
        this.functionsRepository = functionsRepository;
        this.functionsMapper = functionsMapper;
    }

    @Override
    public FunctionsDTO save(FunctionsDTO functionsDTO) {
        log.debug("Request to save Functions : {}", functionsDTO);
        Functions functions = functionsMapper.toEntity(functionsDTO);
        functions = functionsRepository.save(functions);
        return functionsMapper.toDto(functions);
    }

    @Override
    public FunctionsDTO update(FunctionsDTO functionsDTO) {
        log.debug("Request to update Functions : {}", functionsDTO);
        Functions functions = functionsMapper.toEntity(functionsDTO);
        functions = functionsRepository.save(functions);
        return functionsMapper.toDto(functions);
    }

    @Override
    public Optional<FunctionsDTO> partialUpdate(FunctionsDTO functionsDTO) {
        log.debug("Request to partially update Functions : {}", functionsDTO);

        return functionsRepository
            .findById(functionsDTO.getId())
            .map(existingFunctions -> {
                functionsMapper.partialUpdate(existingFunctions, functionsDTO);

                return existingFunctions;
            })
            .map(functionsRepository::save)
            .map(functionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FunctionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Functions");
        return functionsRepository.findAll(pageable).map(functionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FunctionsDTO> findOne(Long id) {
        log.debug("Request to get Functions : {}", id);
        return functionsRepository.findById(id).map(functionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Functions : {}", id);
        functionsRepository.deleteById(id);
    }
}
