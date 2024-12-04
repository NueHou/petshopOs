package com.interno.repositories;

import com.interno.domains.Animal;
import com.interno.domains.dtos.AnimalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    Optional<Animal> findByName(String name);
}
