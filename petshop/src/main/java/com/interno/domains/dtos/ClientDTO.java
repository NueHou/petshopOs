package com.interno.domains.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interno.domains.Animal;
import com.interno.domains.Client;
import com.interno.domains.enums.PersonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {


    protected Integer id;

    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    protected String name;

    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    protected String password;

    @Column(unique = true)
    @NotNull(message = "O campo  não pode ser nulo")
    @CPF
    protected String cpf;

    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    protected String email;

    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    protected Animal animal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate createdAt = LocalDate.now();

    protected Set<Integer> personType = new HashSet<>();

    public ClientDTO (){

    }

    public ClientDTO(Client obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
        this.createdAt = obj.getCreatedAt();
        this.animal = obj.getAnimal();
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

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
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
