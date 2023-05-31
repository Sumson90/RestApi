package com.example.restapi.controller.api;

import com.example.restapi.controller.dao.PetDAO;
import com.example.restapi.controller.dto.EmployeeDto;
import com.example.restapi.controller.dto.EmployeesDto;
import com.example.restapi.controller.dto.EmployeeMapper;
import com.example.restapi.infrastructure.database.entity.EmployeeEntity;
import com.example.restapi.infrastructure.database.entity.PetEntity;
import com.example.restapi.infrastructure.database.repository.EmployeeRepository;
import com.example.restapi.infrastructure.database.repository.PetRepository;
import com.example.restapi.infrastructure.petstore.Pet;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(EmployeesController.EMPLOYEES)
@AllArgsConstructor
public class EmployeesController {
    public static final String EMPLOYEES = "/employees";
    public static final String EMPLOYEES_Id = "/{employeesId}";
    public static final String EMPLOYEES_SALARY_UPDATE = "/{employeesId}/salary";
    public static final String EMPLOYEES_Id_RESULT = "/%s";
    public static final String EMPLOYEES_UPDATE_PET = "/{employeesId}/pet/{petId}";

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    private PetDAO petDao;
    private PetRepository petRepository;

    @GetMapping
    public EmployeesDto employeesDto() {
        return EmployeesDto.of(employeeRepository.findAll().stream()
                .map(a -> employeeMapper.map(a)).toList());
    }

    @GetMapping(value = EMPLOYEES_Id)
    public EmployeeDto employeeDto(@PathVariable Integer employeesId) {
        return employeeRepository.findById(employeesId)
                .map(a -> employeeMapper.map(a)).
                orElseThrow(() -> new EntityNotFoundException(
                        "EmployeeEntity not found, employeeId: [%s]".formatted(employeesId)
                ));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EmployeeDto> addEmployee(
            @Valid @RequestBody EmployeeDto employeeDto

    ) {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .name(employeeDto.getName())
                .surname(employeeDto.getSurname())
                .salary(employeeDto.getSalary())
                .phone(employeeDto.getPhone())
                .email(employeeDto.getEmail())
                .build();

        EmployeeEntity created = employeeRepository.save(employeeEntity);
        return ResponseEntity.created(URI.create(EMPLOYEES + EMPLOYEES_Id_RESULT.formatted(created.getEmployeeId())))
                .build();
    }
    //zwraca pod jakim do naglowka odpowiedzi zostanie doklejona sciezka z informacja gdzie zostal utowrozny

    @PutMapping(EMPLOYEES_Id)
    @Transactional
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer employeesId,
            @Valid @RequestBody EmployeeDto employeeDto
    ) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeesId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "EmpolyeeEintity not found, employeeId: [%s]".formatted(employeesId)
                ));

        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setSurname(employeeDto.getSurname());
        existingEmployee.setSalary(employeeDto.getSalary());
        existingEmployee.setPhone(employeeDto.getPhone());
        existingEmployee.setEmail(employeeDto.getEmail());
        employeeRepository.save(existingEmployee);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping(EMPLOYEES_Id)
    public ResponseEntity<?> deleteEmployee(
            @PathVariable Integer employeesId
    ) {
        Optional<EmployeeEntity> employeeOpt = employeeRepository.findById(employeesId);
        if (employeeOpt.isPresent()) {
            employeeRepository.deleteById(employeesId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(EMPLOYEES_SALARY_UPDATE)
    public ResponseEntity<?> updateEmployeeSalary(
            @PathVariable Integer employeesId,
            @RequestParam BigDecimal newSalary
    ) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeesId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "EmpolyeeEintity not found, employeeId: [%s]".formatted(employeesId)
                ));

        existingEmployee.setSalary(newSalary);
        employeeRepository.save(existingEmployee);
        return ResponseEntity.ok().build();

    }

    @PatchMapping(EMPLOYEES_UPDATE_PET)
    public ResponseEntity<?> updateEmployeeWithPets(
            @PathVariable Integer employeesId,
            @PathVariable Integer petId
    ) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeesId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "EmpolyeeEintity not found, employeeId: [%s]".formatted(employeesId)
                ));
        Pet petFromStore = petDao.getPet(Long.valueOf(petId)).orElseThrow(() -> new RuntimeException(
                "Pet with id: [%s] could not be retrieved".formatted(petId)
        ));
        PetEntity newPet = PetEntity.builder()
                .petStorePetId(petFromStore.getId())
                .name(petFromStore.getName())
                .status(petFromStore.getStatus())
                .category(petFromStore.getCategory())
                .employee(existingEmployee)
                .build();

        petRepository.save(newPet);

        return ResponseEntity.ok().build();


    }


}
