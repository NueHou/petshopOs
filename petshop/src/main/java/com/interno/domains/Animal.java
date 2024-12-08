package com.interno.domains;

import com.interno.domains.dtos.AnimalDTO;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private String race;
    private String type;

    public Animal() {
        super();
    }

    public Animal (AnimalDTO obj){
        this.id = obj.getId();
        this.name = obj.getName();
        this.type = obj.getType();
        this.race = obj.getRace();
    }

    public Animal(Integer id, String type, String race, String name) {
        this.id = id;
        this.type = type;
        this.race = race;
        this.name = name;
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
