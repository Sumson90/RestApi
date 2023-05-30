package com.example.restapi.controller.dto;

import com.example.restapi.controller.dto.EmployeeDto;
import com.example.restapi.infrastructure.database.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeDto map (EmployeeEntity employeeEntity);

}
