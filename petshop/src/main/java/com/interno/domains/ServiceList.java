package com.interno.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interno.domains.dtos.ServiceListDTO;
import com.interno.domains.enums.ServiceType;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "service")
public class ServiceList {

    @Id
    private Integer id;
    private Double valorTotal;
    private String description;


    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "idemployee")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;


    public ServiceList() {

    }

    public ServiceList(Integer id, Client client, Employee employee, String description, Double valorTotal, ServiceType serviceType) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.description = description;
        this.valorTotal = valorTotal;
        this.serviceType = serviceType;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceList other = (ServiceList) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
