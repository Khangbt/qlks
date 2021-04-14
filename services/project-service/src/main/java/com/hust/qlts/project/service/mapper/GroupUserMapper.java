package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.GroupUserDTO;
import com.hust.qlts.project.entity.GroupUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupUserMapper extends EntityMapper<GroupUserDTO, GroupUserEntity>{
}
