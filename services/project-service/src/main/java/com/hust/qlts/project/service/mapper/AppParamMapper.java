package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.AppParamDTO;
import com.hust.qlts.project.entity.AppParamEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppParamMapper extends EntityMapper<AppParamDTO, AppParamEntity>{
}
