package com.example.restapi.controller.dto;

import lombok.*;
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {


        private Integer petId;
        private Long petStorePetId;
        private String name;
        private String category;
}
