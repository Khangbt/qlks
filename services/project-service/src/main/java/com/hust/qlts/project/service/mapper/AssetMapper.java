package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.AssetDTO;
import com.hust.qlts.project.entity.AssetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetMapper extends EntityMapper<AssetDTO, AssetEntity>{
}
