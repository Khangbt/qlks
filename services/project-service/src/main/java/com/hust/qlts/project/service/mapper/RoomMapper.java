package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.RoomDTO;
import com.hust.qlts.project.entity.RoomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper extends EntityMapper<RoomDTO, RoomEntity> {
}
