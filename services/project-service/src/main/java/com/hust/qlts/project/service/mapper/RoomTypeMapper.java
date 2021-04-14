package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.RoomTypeDTO;
import com.hust.qlts.project.entity.RoomTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper extends EntityMapper<RoomTypeDTO, RoomTypeEntity>{
}
