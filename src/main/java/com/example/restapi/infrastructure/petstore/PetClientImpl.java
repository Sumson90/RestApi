package com.example.restapi.infrastructure.petstore;

import com.example.restapi.controller.dao.PetDAO;
import com.example.restapi.infrastructure.petstore.api.PetApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PetClientImpl implements PetDAO {
    private final PetApi petApi;
    private final PetMapper petMapper;
    @Override
    public Optional<Pet> getPet(final Long petId) {
        try {
            return Optional.ofNullable(petApi.getPetById(petId).block())
                    .map(petMapper::map);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

