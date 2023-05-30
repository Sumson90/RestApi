package com.example.restapi.controller.dao;

import com.example.restapi.infrastructure.petstore.Pet;

import java.util.Optional;

public interface PetDAO {
    Optional<Pet> getPet(final Long petId);
}
