package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.RoomServiceDTO;
import com.hust.qlts.project.entity.RoomServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomServiceMapper extends EntityMapper<RoomServiceDTO, RoomServiceEntity>{
}
