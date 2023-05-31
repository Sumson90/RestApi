package com.example.restapi.controller.api;


import com.example.restapi.controller.dto.EmployeeMapper;
import com.example.restapi.util.DtoFixtures;
import com.example.restapi.util.EntityFixture;
import com.example.restapi.infrastructure.database.entity.EmployeeEntity;
import com.example.restapi.infrastructure.database.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeesControllerMockitoTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeesController employeesController;




    @Test
    void thatRetrievingEmployeeWorksCorrectly() {
        // given
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(EntityFixture.someEmployee1().withEmployeeId(123));

        // when
        ResponseEntity<?> result = employeesController.addEmployee(DtoFixtures.someEmployee1());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
