package com.lottery.app.service.impl;

import com.lottery.app.domain.Deposits;
import com.lottery.app.repository.DepositsRepository;
import com.lottery.app.service.DepositsService;
import com.lottery.app.service.dto.DepositsDTO;
import com.lottery.app.service.mapper.DepositsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.Deposits}.
 */
@Service
@Transactional
public class DepositsServiceImpl implements DepositsService {

    private final Logger log = LoggerFactory.getLogger(DepositsServiceImpl.class);

    private final DepositsRepository depositsRepository;

    private final DepositsMapper depositsMapper;

    public DepositsServiceImpl(DepositsRepository depositsRepository, DepositsMapper depositsMapper) {
        this.depositsRepository = depositsRepository;
        this.depositsMapper = depositsMapper;
    }

    @Override
    public DepositsDTO save(DepositsDTO depositsDTO) {
        log.debug("Request to save Deposits : {}", depositsDTO);
        Deposits deposits = depositsMapper.toEntity(depositsDTO);
        deposits = depositsRepository.save(deposits);
        return depositsMapper.toDto(deposits);
    }

    @Override
    public DepositsDTO update(DepositsDTO depositsDTO) {
        log.debug("Request to update Deposits : {}", depositsDTO);
        Deposits deposits = depositsMapper.toEntity(depositsDTO);
        deposits = depositsRepository.save(deposits);
        return depositsMapper.toDto(deposits);
    }

    @Override
    public Optional<DepositsDTO> partialUpdate(DepositsDTO depositsDTO) {
        log.debug("Request to partially update Deposits : {}", depositsDTO);

        return depositsRepository
            .findById(depositsDTO.getId())
            .map(existingDeposits -> {
                depositsMapper.partialUpdate(existingDeposits, depositsDTO);

                return existingDeposits;
            })
            .map(depositsRepository::save)
            .map(depositsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepositsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deposits");
        return depositsRepository.findAll(pageable).map(depositsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepositsDTO> findOne(Long id) {
        log.debug("Request to get Deposits : {}", id);
        return depositsRepository.findById(id).map(depositsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deposits : {}", id);
        depositsRepository.deleteById(id);
    }
}
