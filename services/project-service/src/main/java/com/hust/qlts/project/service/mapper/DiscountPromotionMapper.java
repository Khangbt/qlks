package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.DiscountPromotionDTO;
import com.hust.qlts.project.entity.DiscountPromotionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountPromotionMapper extends EntityMapper<DiscountPromotionDTO, DiscountPromotionEntity>{
}
