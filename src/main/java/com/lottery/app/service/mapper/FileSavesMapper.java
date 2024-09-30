package com.lottery.app.service.mapper;

import com.lottery.app.domain.FileSaves;
import com.lottery.app.service.dto.FileSavesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileSaves} and its DTO {@link FileSavesDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileSavesMapper extends EntityMapper<FileSavesDTO, FileSaves> {}
