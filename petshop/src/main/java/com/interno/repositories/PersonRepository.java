package com.interno.repositories;

import com.interno.domains.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByCpf(String cpf);
    Optional<Person> findByEmail(String email);
}
