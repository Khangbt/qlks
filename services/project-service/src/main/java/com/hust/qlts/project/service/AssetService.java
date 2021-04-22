package com.hust.qlts.project.service;

import com.hust.qlts.project.dto.AssetDTO;
import com.hust.qlts.project.dto.DataPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AssetService {
    DataPage<AssetDTO> searchAsser(AssetDTO dto);
    DataPage<AssetDTO> getPagePartSeach(AssetDTO dto);
    AssetDTO create(AssetDTO partnerDTO);
    AssetDTO update(AssetDTO partnerDTO);
    Boolean delete (Long id);
    AssetDTO findById(Long Id);
    AssetDTO findByCode(String code);
}
