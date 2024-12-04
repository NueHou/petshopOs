package com.interno.domains.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interno.domains.Employee;
import com.interno.domains.enums.PersonType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeDTO {

    protected Integer id;

    @NotNull(message = "O campo sobrenome não pode ser nulo")
    @NotBlank(message = "O campo sobrenome não pode ser vazio")
    protected String name;

    @NotNull(message = "O campo sobrenome não pode ser nulo")
    @NotBlank(message = "O campo sobrenome não pode ser vazio")
    protected String password;

    @Column(unique = true)
    @NotNull(message = "O campo CPF não pode ser nulo")
    @CPF
    protected String cpf;

    @NotNull(message = "O campo sobrenome não pode ser nulo")
    @NotBlank(message = "O campo sobrenome não pode ser vazio")
    protected String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate createdAt = LocalDate.now();

    protected Set<Integer> personType = new HashSet<>();

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
        this.createdAt = obj.getCreatedAt();
        this.personType = obj.getPersonType().stream().map(x -> x.getId()).collect(Collectors.toSet());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Set<PersonType> getPersonType() {
        return personType.stream().map(x -> PersonType.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPersonType(PersonType personType) {
        this.personType.add(personType.getId());
    }
}
