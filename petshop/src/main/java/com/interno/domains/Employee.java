package com.interno.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.interno.domains.dtos.EmployeeDTO;
import com.interno.domains.enums.PersonType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "employees")
public class Employee extends Person{

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<ServiceList> serviceList = new ArrayList<>();

    public Employee(Integer id, String email, String cpf, String name, String password) {
        super(id, email, cpf, name, password);
        addPersonType(PersonType.CLIENT);
        addPersonType(PersonType.EMPLOYEE);
    }

    public Employee(EmployeeDTO obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
        this.createdAt = obj.getCreatedAt();
        this.personType = obj.getPersonType().stream().map(x -> x.getId()).collect(Collectors.toSet());
    }

    public Employee (){
        super();
        addPersonType(PersonType.EMPLOYEE);
    }

    public Employee(List<ServiceList> serviceList) {
        this.serviceList = serviceList;
    }
    public List<ServiceList> getService() {
        return serviceList;
    }
    public void setService(List<ServiceList> serviceList) {
        this.serviceList = serviceList;
    }
}
