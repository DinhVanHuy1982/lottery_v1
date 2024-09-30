package com.lottery.app.service.impl;

import com.lottery.app.domain.Actions;
import com.lottery.app.repository.ActionsRepository;
import com.lottery.app.service.ActionsService;
import com.lottery.app.service.dto.ActionsDTO;
import com.lottery.app.service.mapper.ActionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.Actions}.
 */
@Service
@Transactional
public class ActionsServiceImpl implements ActionsService {

    private final Logger log = LoggerFactory.getLogger(ActionsServiceImpl.class);

    private final ActionsRepository actionsRepository;

    private final ActionsMapper actionsMapper;

    public ActionsServiceImpl(ActionsRepository actionsRepository, ActionsMapper actionsMapper) {
        this.actionsRepository = actionsRepository;
        this.actionsMapper = actionsMapper;
    }

    @Override
    public ActionsDTO save(ActionsDTO actionsDTO) {
        log.debug("Request to save Actions : {}", actionsDTO);
        Actions actions = actionsMapper.toEntity(actionsDTO);
        actions = actionsRepository.save(actions);
        return actionsMapper.toDto(actions);
    }

    @Override
    public ActionsDTO update(ActionsDTO actionsDTO) {
        log.debug("Request to update Actions : {}", actionsDTO);
        Actions actions = actionsMapper.toEntity(actionsDTO);
        actions = actionsRepository.save(actions);
        return actionsMapper.toDto(actions);
    }

    @Override
    public Optional<ActionsDTO> partialUpdate(ActionsDTO actionsDTO) {
        log.debug("Request to partially update Actions : {}", actionsDTO);

        return actionsRepository
            .findById(actionsDTO.getId())
            .map(existingActions -> {
                actionsMapper.partialUpdate(existingActions, actionsDTO);

                return existingActions;
            })
            .map(actionsRepository::save)
            .map(actionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Actions");
        return actionsRepository.findAll(pageable).map(actionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActionsDTO> findOne(Long id) {
        log.debug("Request to get Actions : {}", id);
        return actionsRepository.findById(id).map(actionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Actions : {}", id);
        actionsRepository.deleteById(id);
    }
}
