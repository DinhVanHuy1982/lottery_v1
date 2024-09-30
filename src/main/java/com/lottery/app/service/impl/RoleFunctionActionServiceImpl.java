package com.lottery.app.service.impl;

import com.lottery.app.domain.RoleFunctionAction;
import com.lottery.app.repository.RoleFunctionActionRepository;
import com.lottery.app.service.RoleFunctionActionService;
import com.lottery.app.service.dto.RoleFunctionActionDTO;
import com.lottery.app.service.mapper.RoleFunctionActionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.RoleFunctionAction}.
 */
@Service
@Transactional
public class RoleFunctionActionServiceImpl implements RoleFunctionActionService {

    private final Logger log = LoggerFactory.getLogger(RoleFunctionActionServiceImpl.class);

    private final RoleFunctionActionRepository roleFunctionActionRepository;

    private final RoleFunctionActionMapper roleFunctionActionMapper;

    public RoleFunctionActionServiceImpl(
        RoleFunctionActionRepository roleFunctionActionRepository,
        RoleFunctionActionMapper roleFunctionActionMapper
    ) {
        this.roleFunctionActionRepository = roleFunctionActionRepository;
        this.roleFunctionActionMapper = roleFunctionActionMapper;
    }

    @Override
    public RoleFunctionActionDTO save(RoleFunctionActionDTO roleFunctionActionDTO) {
        log.debug("Request to save RoleFunctionAction : {}", roleFunctionActionDTO);
        RoleFunctionAction roleFunctionAction = roleFunctionActionMapper.toEntity(roleFunctionActionDTO);
        roleFunctionAction = roleFunctionActionRepository.save(roleFunctionAction);
        return roleFunctionActionMapper.toDto(roleFunctionAction);
    }

    @Override
    public RoleFunctionActionDTO update(RoleFunctionActionDTO roleFunctionActionDTO) {
        log.debug("Request to update RoleFunctionAction : {}", roleFunctionActionDTO);
        RoleFunctionAction roleFunctionAction = roleFunctionActionMapper.toEntity(roleFunctionActionDTO);
        roleFunctionAction = roleFunctionActionRepository.save(roleFunctionAction);
        return roleFunctionActionMapper.toDto(roleFunctionAction);
    }

    @Override
    public Optional<RoleFunctionActionDTO> partialUpdate(RoleFunctionActionDTO roleFunctionActionDTO) {
        log.debug("Request to partially update RoleFunctionAction : {}", roleFunctionActionDTO);

        return roleFunctionActionRepository
            .findById(roleFunctionActionDTO.getId())
            .map(existingRoleFunctionAction -> {
                roleFunctionActionMapper.partialUpdate(existingRoleFunctionAction, roleFunctionActionDTO);

                return existingRoleFunctionAction;
            })
            .map(roleFunctionActionRepository::save)
            .map(roleFunctionActionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleFunctionActionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleFunctionActions");
        return roleFunctionActionRepository.findAll(pageable).map(roleFunctionActionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleFunctionActionDTO> findOne(Long id) {
        log.debug("Request to get RoleFunctionAction : {}", id);
        return roleFunctionActionRepository.findById(id).map(roleFunctionActionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleFunctionAction : {}", id);
        roleFunctionActionRepository.deleteById(id);
    }
}
