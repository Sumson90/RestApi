package com.example.restapi.integration.suport;

import com.example.restapi.controller.api.EmployeesController;
import com.example.restapi.controller.dto.EmployeeDto;
import com.example.restapi.controller.dto.EmployeesDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface EmployeesControllerTestSupport {

    RequestSpecification requestSpecification();
    default EmployeesDto listEmployees() {
        return requestSpecification()
                .get(EmployeesController.EMPLOYEES)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(EmployeesDto.class);
    }

    default EmployeeDto getEmployee(final String path) {
        return requestSpecification()
                .get(path)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(EmployeeDto.class);
    }
    default EmployeeDto getEmployeeById(final Integer employeeId) {
        return requestSpecification()
                .get(EmployeesController.EMPLOYEES + EmployeesController.EMPLOYEES_Id, employeeId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(EmployeeDto.class);
    }
    default ExtractableResponse<Response> saveEmployee(final EmployeeDto employeeDTO) {
        return requestSpecification()
                .body(employeeDTO)
                .post(EmployeesController.EMPLOYEES)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }
    default void updateEmployeeByPet(final Integer employeeId, final Long petId) {
        String endpoint = EmployeesController.EMPLOYEES
                + EmployeesController.EMPLOYEES_UPDATE_PET;
        requestSpecification()
                .patch(endpoint, employeeId, petId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}

