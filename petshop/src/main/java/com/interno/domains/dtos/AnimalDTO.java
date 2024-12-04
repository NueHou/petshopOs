package com.interno.domains.dtos;

import com.interno.domains.Animal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AnimalDTO {


    protected Integer id;

    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    protected String name;



    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    protected String race;

    @NotNull(message = "O campo  não pode ser nulo")
    @NotBlank(message = "O campo  não pode ser vazio")
    protected String type;

    public AnimalDTO() {
    }

    public AnimalDTO (Animal obj){
        this.id = obj.getId();
        this.name = obj.getName();
        this.type = obj.getType();
        this.race = obj.getRace();
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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
