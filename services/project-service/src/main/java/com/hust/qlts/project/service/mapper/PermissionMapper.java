package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.PermissionDTO;
import com.hust.qlts.project.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends EntityMapper<PermissionDTO, PermissionEntity>{
}
