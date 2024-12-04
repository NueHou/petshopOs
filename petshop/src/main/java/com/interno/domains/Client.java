package com.interno.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.interno.domains.dtos.ClientDTO;
import com.interno.domains.enums.PersonType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "clients")
public class Client extends Person{

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<ServiceList> serviceList = new ArrayList<>();

    @OneToOne
    private Animal animal;

    public Client(Integer id, String email, String cpf, String name, String password, Animal animal) {
        super(id, email, cpf, name, password);
        this.animal = animal;
        addPersonType(PersonType.CLIENT);
    }

    public Client(ClientDTO obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
        this.createdAt = obj.getCreatedAt();
        this.animal = obj.getAnimal();
        this.personType = obj.getPersonType().stream().map(x -> x.getId()).collect(Collectors.toSet());
    }


    public Client(){
        super();
        addPersonType(PersonType.CLIENT);
    }
    public Client(List<ServiceList> serviceList) {
        this.serviceList = serviceList;
    }
    public List<ServiceList> getService() {
        return serviceList;
    }
    public void setService(List<ServiceList> serviceList) {
        this.serviceList = serviceList;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
