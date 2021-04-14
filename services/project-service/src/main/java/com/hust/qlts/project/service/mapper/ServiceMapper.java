package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.ServiceDTO;
import com.hust.qlts.project.entity.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper extends EntityMapper<ServiceDTO, ServiceEntity>{
}
