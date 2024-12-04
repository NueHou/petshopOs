package com.interno.repositories;

import com.interno.domains.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByCpf(String cpf);
    Optional<Client> findByEmail(String email);
}
