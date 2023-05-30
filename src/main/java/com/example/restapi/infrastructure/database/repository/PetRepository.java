package com.example.restapi.infrastructure.database.repository;

import com.example.restapi.infrastructure.database.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<PetEntity , Integer> {
}
