package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.ImageDTO;
import com.hust.qlts.project.entity.ImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDTO, ImageEntity>{

}
