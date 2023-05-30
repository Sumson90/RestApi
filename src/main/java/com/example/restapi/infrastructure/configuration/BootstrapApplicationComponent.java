package com.example.restapi.infrastructure.configuration;

import com.example.restapi.infrastructure.database.entity.EmployeeEntity;
import com.example.restapi.infrastructure.database.repository.EmployeeRepository;
import com.example.restapi.infrastructure.database.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Slf4j
@Component
@AllArgsConstructor
public class BootstrapApplicationComponent implements ApplicationListener<ContextRefreshedEvent> {
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;


    @Override
    @Transactional
    public void onApplicationEvent(final @NonNull ContextRefreshedEvent event) {
//        if (employeeRepository.findAll().size()>0){
//            return;
//        }
        petRepository.deleteAll();
        employeeRepository.deleteAll();

        employeeRepository.save(EmployeeEntity.builder()
                        .name("Strfan")
                        .surname("Ziomek")
                        .salary(new BigDecimal("5230.0"))
                        .phone("+48 123 456 789")
                        .email("example1@email.com")
                .build());
        employeeRepository.save(EmployeeEntity.builder()
                        .name("Marek")
                        .surname("Kaminski")
                        .salary(new BigDecimal("4000.0"))
                        .phone("+48 123 456 789")
                        .email("example2@email.com")
                .build());
        employeeRepository.save(EmployeeEntity.builder()
                        .name("Lukasz")
                        .surname("Bombel")
                        .salary(new BigDecimal("3000.0"))
                        .phone("+48 123 456 789")
                        .email("example3@email.com")
                .build());
    }
}
