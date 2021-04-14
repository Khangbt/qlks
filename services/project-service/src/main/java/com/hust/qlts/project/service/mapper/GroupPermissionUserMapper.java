package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.GroupPermissionUserDTO;
import com.hust.qlts.project.entity.GroupPermissionUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupPermissionUserMapper extends EntityMapper<GroupPermissionUserDTO, GroupPermissionUserEntity>{
}
