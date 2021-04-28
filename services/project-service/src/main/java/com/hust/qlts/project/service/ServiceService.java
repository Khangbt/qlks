package com.hust.qlts.project.service;

import com.hust.qlts.project.dto.DataPage;
import com.hust.qlts.project.dto.ServiceDTO;

public interface ServiceService {
    DataPage<ServiceDTO> searchService(ServiceDTO dto);
    DataPage<ServiceDTO> getPagePartSeach(ServiceDTO dto);
    ServiceDTO create(ServiceDTO dto);
    ServiceDTO update(ServiceDTO dto);
    ServiceDTO delete (Long id);
    ServiceDTO findById(Long Id);
    ServiceDTO findByCode(String code);
}