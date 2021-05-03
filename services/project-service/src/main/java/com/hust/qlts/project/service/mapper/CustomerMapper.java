package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.CustomerDTO;
import com.hust.qlts.project.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, CustomerEntity> {
}
