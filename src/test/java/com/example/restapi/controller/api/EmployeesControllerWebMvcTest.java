package com.example.restapi.controller.api;

import com.example.restapi.controller.dao.PetDAO;
import com.example.restapi.controller.dto.EmployeeDto;
import com.example.restapi.controller.dto.EmployeeMapper;
import com.example.restapi.util.DtoFixtures;
import com.example.restapi.util.EntityFixture;
import com.example.restapi.infrastructure.database.entity.EmployeeEntity;
import com.example.restapi.infrastructure.database.repository.EmployeeRepository;
import com.example.restapi.infrastructure.database.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeesController.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeesControllerWebMvcTest {
    private final MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private PetRepository petRepository;
    @MockBean
    private PetDAO petDAO;
    @MockBean
    private EmployeeMapper employeeMapper;

    //hamcrest

    @Test
    void thatEmployeeCanBeRetrievedCorrectly() throws Exception {
        // given
        int employeeId = 123;
        EmployeeEntity employeeEntity = EntityFixture.someEmployee1().withEmployeeId(employeeId);
        EmployeeDto employeeDTO = DtoFixtures.someEmployee1().withEmployeeId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeEntity));
        when(employeeMapper.map(any(EmployeeEntity.class))).thenReturn(employeeDTO);
        // when, then
        String endpoint = EmployeesController.EMPLOYEES + EmployeesController.EMPLOYEES_Id;
        mockMvc.perform(get(endpoint, employeeId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", Matchers.is(employeeDTO.getEmployeeId())))
                .andExpect(jsonPath("$.name", Matchers.is(employeeDTO.getName())))
                .andExpect(jsonPath("$.surname", Matchers.is(employeeDTO.getSurname())))
                .andExpect(jsonPath("$.salary", Matchers.is(employeeDTO.getSalary()), BigDecimal.class))
                .andExpect(jsonPath("$.phone", Matchers.is(employeeDTO.getPhone())))
                .andExpect(jsonPath("$.email", Matchers.is(employeeDTO.getEmail())));
    }
    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        // given
        final var request = """
         {
         "email": "badEmail"
         }
          """;
        // when, then
        mockMvc.perform(
                        post(EmployeesController.EMPLOYEES)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorId", Matchers.notNullValue()));
    }

    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
        // given
        final var request = """
        {
        "phone": "%s"
         }
        """.formatted(phone);
        when(employeeRepository.save(any(EmployeeEntity.class)))
                .thenReturn(EntityFixture.someEmployee1().withEmployeeId(123));
        // when, then
        if (correctPhone) {
            String expectedRedirect = EmployeesController.EMPLOYEES
                    + EmployeesController.EMPLOYEES_Id_RESULT.formatted(123);
            mockMvc.perform(post(EmployeesController.EMPLOYEES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isCreated())
                    .andExpect(redirectedUrl(expectedRedirect));
        } else {
            mockMvc.perform(post(EmployeesController.EMPLOYEES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorId", Matchers.notNullValue()));
        }
    }
    @SuppressWarnings("unused")
    // Test cases source: https://stackoverflow.com/questions/8634139/phone-validation-regex
    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 7777"),
                Arguments.of(false, "+48 (21)"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "555-5555-555"),
                Arguments.of(true, "+48 504 203 260")
        );
    }
}

