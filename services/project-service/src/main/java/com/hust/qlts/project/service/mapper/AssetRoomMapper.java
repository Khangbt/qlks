package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.AssetRoomDTO;
import com.hust.qlts.project.entity.AssetRoomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetRoomMapper extends EntityMapper<AssetRoomDTO, AssetRoomEntity>{
}
