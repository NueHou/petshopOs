package com.interno.domains.enums;

public enum ServiceType {

    BATH(0, "ROLE_BATHS"), GROOM(1, "ROLE_GROOMS"), APPOINTMENT(2, "ROLE_APPOINTMENTS");

    private Integer id;
    private String serviceType;

    ServiceType(Integer id, String serviceType) {
        this.id = id;
        this.serviceType = serviceType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public static ServiceType toEnum(Integer id){
        if(id==null) return null;
        for(ServiceType x : ServiceType.values()){
            if(id.equals(x.getId())){
                return x;
            }
        }
        throw new IllegalArgumentException("Tipo de serviço invalido!");
    }
}
