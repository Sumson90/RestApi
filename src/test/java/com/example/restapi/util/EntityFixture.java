package com.example.restapi.util;

import com.example.restapi.controller.dto.EmployeeDto;
import com.example.restapi.controller.dto.PetDto;
import com.example.restapi.infrastructure.database.entity.EmployeeEntity;
import lombok.experimental.UtilityClass;


import java.math.BigDecimal;

@UtilityClass
public class EntityFixture {
    public static EmployeeEntity someEmployee1(){
        return EmployeeEntity.builder()
                .name("Agieniszka")
                .surname("Example")
                .salary(new BigDecimal("12345.0").setScale(2))
                .phone("+48 123 456 123")
                .email("example@email.pl")
                .build();
    }
    public static EmployeeEntity someEmployee2(){
        return EmployeeEntity.builder()
                .name("Tomek")
                .surname("Ziomek")
                .salary(new BigDecimal("77777.0").setScale(2))
                .phone("+48 123 456 123")
                .email("example@email.pl")
                .build();
    }
    public static EmployeeEntity someEmployee3(){
        return EmployeeEntity.builder()
                .name("Marek")
                .surname("Kaminski")
                .salary(new BigDecimal("12345.0"))
                .phone("+48 123 456 123")
                .email("example@email.pl")
                .build();
    }
}
