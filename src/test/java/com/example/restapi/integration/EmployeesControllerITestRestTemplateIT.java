package com.example.restapi.integration;

import com.example.restapi.controller.dto.EmployeesDto;
import com.example.restapi.integration.configuration.AbstractIntegrationTest;
import com.example.restapi.util.DtoFixtures;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeesControllerITestRestTemplateIT extends AbstractIntegrationTest {
    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate;
    @Test
    public void thatEmployeesListingWorksCorrectly() {
        String url = "http://localhost:%s/example/employees".formatted(port);

        this.restTemplate.postForEntity(url, DtoFixtures.someEmployee1(), EmployeesDto.class);

        ResponseEntity<EmployeesDto> result = this.restTemplate.getForEntity(url, EmployeesDto.class);
        EmployeesDto body = result.getBody();
        Assertions.assertThat(body).isNotNull();
        Assertions.assertThat(body.getEmployee()).hasSizeGreaterThan(0);


    }


}
