package com.example.restapi.infrastructure.petstore;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private Long id;
    private String name;
    private String status;
    private String category;
}
