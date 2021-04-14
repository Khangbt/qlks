package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.HistoryDTO;
import com.hust.qlts.project.entity.HistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper extends EntityMapper<HistoryDTO, HistoryEntity>{
}
