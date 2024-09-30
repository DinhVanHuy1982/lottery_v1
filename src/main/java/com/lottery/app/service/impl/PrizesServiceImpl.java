package com.lottery.app.service.impl;

import com.lottery.app.domain.Prizes;
import com.lottery.app.repository.PrizesRepository;
import com.lottery.app.service.PrizesService;
import com.lottery.app.service.dto.PrizesDTO;
import com.lottery.app.service.mapper.PrizesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.Prizes}.
 */
@Service
@Transactional
public class PrizesServiceImpl implements PrizesService {

    private final Logger log = LoggerFactory.getLogger(PrizesServiceImpl.class);

    private final PrizesRepository prizesRepository;

    private final PrizesMapper prizesMapper;

    public PrizesServiceImpl(PrizesRepository prizesRepository, PrizesMapper prizesMapper) {
        this.prizesRepository = prizesRepository;
        this.prizesMapper = prizesMapper;
    }

    @Override
    public PrizesDTO save(PrizesDTO prizesDTO) {
        log.debug("Request to save Prizes : {}", prizesDTO);
        Prizes prizes = prizesMapper.toEntity(prizesDTO);
        prizes = prizesRepository.save(prizes);
        return prizesMapper.toDto(prizes);
    }

    @Override
    public PrizesDTO update(PrizesDTO prizesDTO) {
        log.debug("Request to update Prizes : {}", prizesDTO);
        Prizes prizes = prizesMapper.toEntity(prizesDTO);
        prizes = prizesRepository.save(prizes);
        return prizesMapper.toDto(prizes);
    }

    @Override
    public Optional<PrizesDTO> partialUpdate(PrizesDTO prizesDTO) {
        log.debug("Request to partially update Prizes : {}", prizesDTO);

        return prizesRepository
            .findById(prizesDTO.getId())
            .map(existingPrizes -> {
                prizesMapper.partialUpdate(existingPrizes, prizesDTO);

                return existingPrizes;
            })
            .map(prizesRepository::save)
            .map(prizesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrizesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prizes");
        return prizesRepository.findAll(pageable).map(prizesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrizesDTO> findOne(Long id) {
        log.debug("Request to get Prizes : {}", id);
        return prizesRepository.findById(id).map(prizesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prizes : {}", id);
        prizesRepository.deleteById(id);
    }
}
