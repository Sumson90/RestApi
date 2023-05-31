package com.example.restapi.integration;

import com.example.restapi.controller.dto.EmployeeDto;
import com.example.restapi.controller.dto.EmployeesDto;
import com.example.restapi.integration.configuration.RestAssuredIntegrationTestBase;
import com.example.restapi.integration.suport.EmployeesControllerTestSupport;
import com.example.restapi.integration.suport.WireMockTestSupport;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.regex.Pattern;

import static com.example.restapi.util.DtoFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;


public class EmployeesControllerITestRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements EmployeesControllerTestSupport, WireMockTestSupport {
    @Test
    void thatEmployeesListCanBeRetrievedCorrectly() {
        // given
        EmployeeDto employee1 = someEmployee1();
        EmployeeDto employee2 = someEmployee2();
        // when
        saveEmployee(employee1);
        saveEmployee(employee2);

        EmployeesDto retrievedEmployees = listEmployees();
        // then
        Assertions.assertThat(retrievedEmployees.getEmployee())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("employeeId")
                .containsAnyOf(employee1.withPets(Set.of()), employee2.withPets(Set.of()));

    }

    @Test
    void thatEmployeeCanBeCreatedCorrectly() {
        // given
        EmployeeDto employee1 = someEmployee1();
        // when
        ExtractableResponse<Response> response = saveEmployee(employee1);
        // then
        String responseAsString = response.body().asString();
        assertThat(responseAsString).isEmpty();
        assertThat(response.headers().get("Location").getValue())
                .matches(Pattern.compile("/employees/\\d"));
    }

    @Test
    void thatCreatedEmployeeCanBeRetrievedCorrectly() {
        // given
        EmployeeDto employee1 = someEmployee1();
        // when
        ExtractableResponse<Response> response = saveEmployee(employee1);
        String employeeDetailsPath = response.headers().get("Location").getValue();
        EmployeeDto retrievedEmployee = getEmployee(employeeDetailsPath);
        // then
        assertThat(retrievedEmployee)
                .usingRecursiveComparison()
                .ignoringFields("employeeId")
                .isEqualTo(employee1.withPets(Set.of()));
    }

//    @Test
//    void thatEmployeesCanBeUpdatedWithPetCorrectly() {
//        // given
//        long petId = 4;
//        EmployeeDto employee1 = someEmployee1();
//        ExtractableResponse<Response> response = saveEmployee(employee1);
//        EmployeeDto retrievedEmployee = getEmployee(response.header("Location"));
//        stubForPet(wireMockServer, petId);
//
//        // when
//        updateEmployeeByPet(retrievedEmployee.getEmployeeId(), petId);
//
//        // then
//        EmployeeDto employeeWithPet = getEmployeeById(retrievedEmployee.getEmployeeId());
//        assertThat(employeeWithPet)
//                .usingRecursiveComparison()
//                .ignoringFields("employeeId", "petId")
//                .isEqualTo(employee1.withPets(Set.of(somePet())));
//    }
}


