package com.example.restapi.controller.dto;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmployeesDto {
    private List<EmployeeDto> employee;
}
