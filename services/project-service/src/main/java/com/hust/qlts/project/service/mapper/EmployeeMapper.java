package com.hust.qlts.project.service.mapper;

import com.hust.qlts.project.dto.EmployeeDTO;
import com.hust.qlts.project.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, EmployeeEntity>{
}
