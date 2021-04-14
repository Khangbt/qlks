package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.WorkScheduleDTO;
import com.hust.qlts.project.entity.WorkScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkScheduleMapper extends EntityMapper<WorkScheduleDTO, WorkScheduleEntity>{
}
