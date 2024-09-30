package com.lottery.app.service.impl;

import com.lottery.app.domain.ResultsEveryDay;
import com.lottery.app.repository.ResultsEveryDayRepository;
import com.lottery.app.service.ResultsEveryDayService;
import com.lottery.app.service.dto.ResultsEveryDayDTO;
import com.lottery.app.service.mapper.ResultsEveryDayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.ResultsEveryDay}.
 */
@Service
@Transactional
public class ResultsEveryDayServiceImpl implements ResultsEveryDayService {

    private final Logger log = LoggerFactory.getLogger(ResultsEveryDayServiceImpl.class);

    private final ResultsEveryDayRepository resultsEveryDayRepository;

    private final ResultsEveryDayMapper resultsEveryDayMapper;

    public ResultsEveryDayServiceImpl(ResultsEveryDayRepository resultsEveryDayRepository, ResultsEveryDayMapper resultsEveryDayMapper) {
        this.resultsEveryDayRepository = resultsEveryDayRepository;
        this.resultsEveryDayMapper = resultsEveryDayMapper;
    }

    @Override
    public ResultsEveryDayDTO save(ResultsEveryDayDTO resultsEveryDayDTO) {
        log.debug("Request to save ResultsEveryDay : {}", resultsEveryDayDTO);
        ResultsEveryDay resultsEveryDay = resultsEveryDayMapper.toEntity(resultsEveryDayDTO);
        resultsEveryDay = resultsEveryDayRepository.save(resultsEveryDay);
        return resultsEveryDayMapper.toDto(resultsEveryDay);
    }

    @Override
    public ResultsEveryDayDTO update(ResultsEveryDayDTO resultsEveryDayDTO) {
        log.debug("Request to update ResultsEveryDay : {}", resultsEveryDayDTO);
        ResultsEveryDay resultsEveryDay = resultsEveryDayMapper.toEntity(resultsEveryDayDTO);
        resultsEveryDay = resultsEveryDayRepository.save(resultsEveryDay);
        return resultsEveryDayMapper.toDto(resultsEveryDay);
    }

    @Override
    public Optional<ResultsEveryDayDTO> partialUpdate(ResultsEveryDayDTO resultsEveryDayDTO) {
        log.debug("Request to partially update ResultsEveryDay : {}", resultsEveryDayDTO);

        return resultsEveryDayRepository
            .findById(resultsEveryDayDTO.getId())
            .map(existingResultsEveryDay -> {
                resultsEveryDayMapper.partialUpdate(existingResultsEveryDay, resultsEveryDayDTO);

                return existingResultsEveryDay;
            })
            .map(resultsEveryDayRepository::save)
            .map(resultsEveryDayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResultsEveryDayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResultsEveryDays");
        return resultsEveryDayRepository.findAll(pageable).map(resultsEveryDayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResultsEveryDayDTO> findOne(Long id) {
        log.debug("Request to get ResultsEveryDay : {}", id);
        return resultsEveryDayRepository.findById(id).map(resultsEveryDayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResultsEveryDay : {}", id);
        resultsEveryDayRepository.deleteById(id);
    }
}
