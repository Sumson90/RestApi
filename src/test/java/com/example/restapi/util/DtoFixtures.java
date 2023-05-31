package com.example.restapi.util;

import com.example.restapi.controller.dto.EmployeeDto;
import com.example.restapi.controller.dto.PetDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
@UtilityClass

public class DtoFixtures {

    public static EmployeeDto someEmployee1(){
        return EmployeeDto.builder()
                .name("Agieniszka")
                .surname("Example")
                .salary(new BigDecimal("12345"))
                .phone("+48 123 456 123")
                .email("example@email.pl")
                .build();
    }
    public static EmployeeDto someEmployee2(){
        return EmployeeDto.builder()
                .name("Tomek")
                .surname("Ziomek")
                .salary(new BigDecimal("77777"))
                .phone("+48 123 456 123")
                .email("example@email.pl")
                .build();
    }
    public static PetDto somePet(){
        return PetDto.builder()
                .petId(1)
                .petStorePetId(4L)
                .name("lion")
                .category("Dogs")
                .build();
    }
}
