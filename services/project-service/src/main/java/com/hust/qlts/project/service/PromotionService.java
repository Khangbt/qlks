package com.hust.qlts.project.service;

import com.hust.qlts.project.dto.DataPage;
import com.hust.qlts.project.dto.promotionDTO;

public interface PromotionService {
    DataPage<promotionDTO> searchPromotion(promotionDTO dto);
    DataPage<promotionDTO> getPagePartSeach(promotionDTO dto);
    promotionDTO create(promotionDTO dto);
    promotionDTO update(promotionDTO dto);
    promotionDTO delete (Long id);
    promotionDTO findById(Long Id);
    promotionDTO findByCode(String code);
}
