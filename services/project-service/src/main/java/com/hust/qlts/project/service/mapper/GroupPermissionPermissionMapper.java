package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.GroupPermissionPermissionDTO;
import com.hust.qlts.project.entity.GroupPermissionPermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupPermissionPermissionMapper extends EntityMapper<GroupPermissionPermissionDTO, GroupPermissionPermissionEntity>{
}
