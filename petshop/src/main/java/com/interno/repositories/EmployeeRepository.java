package com.interno.repositories;

import com.interno.domains.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByCpf(String cpf);
    Optional<Employee> findByEmail(String email);
}
