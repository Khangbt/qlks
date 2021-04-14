package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.PermissionGroupUserDTO;
import com.hust.qlts.project.entity.PermissionGroupUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionGroupUserMapper extends EntityMapper<PermissionGroupUserDTO, PermissionGroupUserEntity>{
}
