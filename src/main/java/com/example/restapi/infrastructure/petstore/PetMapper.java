package com.example.restapi.infrastructure.petstore;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component
public class PetMapper {
    public Pet map(com.example.restapi.infrastructure.petstore.model.Pet pet) {
        return Pet.builder()
                .id(pet.getId())
                .name(pet.getName())
                .status(Optional.ofNullable(pet.getStatus()).map(status -> status.getValue()).orElse(null))
                .category(Optional.ofNullable(pet.getCategory()).map(category -> category.getName()).orElse(null))
                .build();
    }
}
