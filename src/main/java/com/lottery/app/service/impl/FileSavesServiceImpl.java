package com.lottery.app.service.impl;

import com.lottery.app.domain.FileSaves;
import com.lottery.app.repository.FileSavesRepository;
import com.lottery.app.service.FileSavesService;
import com.lottery.app.service.dto.FileSavesDTO;
import com.lottery.app.service.mapper.FileSavesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lottery.app.domain.FileSaves}.
 */
@Service
@Transactional
public class FileSavesServiceImpl implements FileSavesService {

    private final Logger log = LoggerFactory.getLogger(FileSavesServiceImpl.class);

    private final FileSavesRepository fileSavesRepository;

    private final FileSavesMapper fileSavesMapper;

    public FileSavesServiceImpl(FileSavesRepository fileSavesRepository, FileSavesMapper fileSavesMapper) {
        this.fileSavesRepository = fileSavesRepository;
        this.fileSavesMapper = fileSavesMapper;
    }

    @Override
    public FileSavesDTO save(FileSavesDTO fileSavesDTO) {
        log.debug("Request to save FileSaves : {}", fileSavesDTO);
        FileSaves fileSaves = fileSavesMapper.toEntity(fileSavesDTO);
        fileSaves = fileSavesRepository.save(fileSaves);
        return fileSavesMapper.toDto(fileSaves);
    }

    @Override
    public FileSavesDTO update(FileSavesDTO fileSavesDTO) {
        log.debug("Request to update FileSaves : {}", fileSavesDTO);
        FileSaves fileSaves = fileSavesMapper.toEntity(fileSavesDTO);
        fileSaves = fileSavesRepository.save(fileSaves);
        return fileSavesMapper.toDto(fileSaves);
    }

    @Override
    public Optional<FileSavesDTO> partialUpdate(FileSavesDTO fileSavesDTO) {
        log.debug("Request to partially update FileSaves : {}", fileSavesDTO);

        return fileSavesRepository
            .findById(fileSavesDTO.getId())
            .map(existingFileSaves -> {
                fileSavesMapper.partialUpdate(existingFileSaves, fileSavesDTO);

                return existingFileSaves;
            })
            .map(fileSavesRepository::save)
            .map(fileSavesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileSavesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileSaves");
        return fileSavesRepository.findAll(pageable).map(fileSavesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileSavesDTO> findOne(Long id) {
        log.debug("Request to get FileSaves : {}", id);
        return fileSavesRepository.findById(id).map(fileSavesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileSaves : {}", id);
        fileSavesRepository.deleteById(id);
    }
}
