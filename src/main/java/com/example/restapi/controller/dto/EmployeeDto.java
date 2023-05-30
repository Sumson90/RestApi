package com.example.restapi.controller.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {


    private Integer employeeId;

    private String name;


    private String surname;


    private BigDecimal salary;
    @Size(min = 7, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")

    private String phone;
    @Email
    private String email;
//    @OneToMany(fetch = FetchType.EAGER,mappedBy = "employee")
//    private Setter<PetEntity> pets;

}
