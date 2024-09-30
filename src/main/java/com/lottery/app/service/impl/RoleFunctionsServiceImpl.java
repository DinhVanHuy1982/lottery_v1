package com.lottery.app.service.impl;

import com.lottery.app.domain.RoleFunctions;
import com.lottery.app.repository.RoleFunctionsRepository;
import com.lottery.app.service.RoleFunctionsService;
import com.lottery.app.service.dto.RoleFunctionsDTO;
import com.lottery.app.service.mapper.RoleFunctionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.RoleFunctions}.
 */
@Service
@Transactional
public class RoleFunctionsServiceImpl implements RoleFunctionsService {

    private final Logger log = LoggerFactory.getLogger(RoleFunctionsServiceImpl.class);

    private final RoleFunctionsRepository roleFunctionsRepository;

    private final RoleFunctionsMapper roleFunctionsMapper;

    public RoleFunctionsServiceImpl(RoleFunctionsRepository roleFunctionsRepository, RoleFunctionsMapper roleFunctionsMapper) {
        this.roleFunctionsRepository = roleFunctionsRepository;
        this.roleFunctionsMapper = roleFunctionsMapper;
    }

    @Override
    public RoleFunctionsDTO save(RoleFunctionsDTO roleFunctionsDTO) {
        log.debug("Request to save RoleFunctions : {}", roleFunctionsDTO);
        RoleFunctions roleFunctions = roleFunctionsMapper.toEntity(roleFunctionsDTO);
        roleFunctions = roleFunctionsRepository.save(roleFunctions);
        return roleFunctionsMapper.toDto(roleFunctions);
    }

    @Override
    public RoleFunctionsDTO update(RoleFunctionsDTO roleFunctionsDTO) {
        log.debug("Request to update RoleFunctions : {}", roleFunctionsDTO);
        RoleFunctions roleFunctions = roleFunctionsMapper.toEntity(roleFunctionsDTO);
        roleFunctions = roleFunctionsRepository.save(roleFunctions);
        return roleFunctionsMapper.toDto(roleFunctions);
    }

    @Override
    public Optional<RoleFunctionsDTO> partialUpdate(RoleFunctionsDTO roleFunctionsDTO) {
        log.debug("Request to partially update RoleFunctions : {}", roleFunctionsDTO);

        return roleFunctionsRepository
            .findById(roleFunctionsDTO.getId())
            .map(existingRoleFunctions -> {
                roleFunctionsMapper.partialUpdate(existingRoleFunctions, roleFunctionsDTO);

                return existingRoleFunctions;
            })
            .map(roleFunctionsRepository::save)
            .map(roleFunctionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleFunctionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleFunctions");
        return roleFunctionsRepository.findAll(pageable).map(roleFunctionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleFunctionsDTO> findOne(Long id) {
        log.debug("Request to get RoleFunctions : {}", id);
        return roleFunctionsRepository.findById(id).map(roleFunctionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleFunctions : {}", id);
        roleFunctionsRepository.deleteById(id);
    }
}
