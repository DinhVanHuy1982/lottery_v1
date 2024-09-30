package com.lottery.app.service.impl;

import com.lottery.app.domain.AppParams;
import com.lottery.app.repository.AppParamsRepository;
import com.lottery.app.service.AppParamsService;
import com.lottery.app.service.dto.AppParamsDTO;
import com.lottery.app.service.mapper.AppParamsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.AppParams}.
 */
@Service
@Transactional
public class AppParamsServiceImpl implements AppParamsService {

    private final Logger log = LoggerFactory.getLogger(AppParamsServiceImpl.class);

    private final AppParamsRepository appParamsRepository;

    private final AppParamsMapper appParamsMapper;

    public AppParamsServiceImpl(AppParamsRepository appParamsRepository, AppParamsMapper appParamsMapper) {
        this.appParamsRepository = appParamsRepository;
        this.appParamsMapper = appParamsMapper;
    }

    @Override
    public AppParamsDTO save(AppParamsDTO appParamsDTO) {
        log.debug("Request to save AppParams : {}", appParamsDTO);
        AppParams appParams = appParamsMapper.toEntity(appParamsDTO);
        appParams = appParamsRepository.save(appParams);
        return appParamsMapper.toDto(appParams);
    }

    @Override
    public AppParamsDTO update(AppParamsDTO appParamsDTO) {
        log.debug("Request to update AppParams : {}", appParamsDTO);
        AppParams appParams = appParamsMapper.toEntity(appParamsDTO);
        appParams = appParamsRepository.save(appParams);
        return appParamsMapper.toDto(appParams);
    }

    @Override
    public Optional<AppParamsDTO> partialUpdate(AppParamsDTO appParamsDTO) {
        log.debug("Request to partially update AppParams : {}", appParamsDTO);

        return appParamsRepository
            .findById(appParamsDTO.getId())
            .map(existingAppParams -> {
                appParamsMapper.partialUpdate(existingAppParams, appParamsDTO);

                return existingAppParams;
            })
            .map(appParamsRepository::save)
            .map(appParamsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppParamsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppParams");
        return appParamsRepository.findAll(pageable).map(appParamsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppParamsDTO> findOne(Long id) {
        log.debug("Request to get AppParams : {}", id);
        return appParamsRepository.findById(id).map(appParamsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppParams : {}", id);
        appParamsRepository.deleteById(id);
    }
}
