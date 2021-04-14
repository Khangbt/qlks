package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.GroupPermissionDTO;
import com.hust.qlts.project.entity.GroupPermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupPermissionMapper extends EntityMapper<GroupPermissionDTO, GroupPermissionEntity>{
}
