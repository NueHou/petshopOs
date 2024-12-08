package com.interno.domains.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interno.domains.ServiceList;
import com.interno.domains.enums.ServiceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class ServiceListDTO {

    private Integer id;

    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    private Double fullPrice;
    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    private String description;


    private ServiceType serviceType;
    private Integer employee;
    private Integer client;

    public ServiceListDTO(){

    }

    public ServiceListDTO(ServiceList obj){
        this.id = obj.getId();
        this.fullPrice = obj.getFullPrice();
        this.description = obj.getDescription();
        this.client = obj.getClient().getId();
        this.employee = obj.getEmployee().getId();
        this.serviceType = obj.getServiceType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
