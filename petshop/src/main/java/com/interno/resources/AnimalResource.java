package com.interno.resources;

import com.interno.domains.Animal;
import com.interno.domains.dtos.AnimalDTO;
import com.interno.services.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/animal")
public class AnimalResource {

    @Autowired
    private AnimalService animalService;


    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping
    public ResponseEntity <List<AnimalDTO>> findAll(){
        return ResponseEntity.ok().body(animalService.findAll());
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable Integer id){
        Animal obj = this.animalService.findById(id);
        return ResponseEntity.ok().body(new AnimalDTO(obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping(value = "/animal/name")
    public ResponseEntity <AnimalDTO> findByName(@PathVariable String name){
        Animal obj = this.animalService.findByName(name);
        return ResponseEntity.ok().body(new AnimalDTO(obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<AnimalDTO> create(@Valid @RequestBody AnimalDTO objDto){
        Animal newObj = animalService.create(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AnimalDTO> update(@PathVariable Integer id, @Valid @RequestBody AnimalDTO objDto){
        Animal Obj = animalService.update(id, objDto);
        return ResponseEntity.ok().body(new AnimalDTO(Obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AnimalDTO> delete(@PathVariable Integer id){
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
