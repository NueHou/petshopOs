package com.interno.resources;

import com.interno.domains.ServiceList;
import com.interno.domains.dtos.ServiceListDTO;
import com.interno.services.ServiceListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class ServiceResource {

    @Autowired
    private ServiceListService serviceListService;

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping
    public ResponseEntity<List<ServiceListDTO>> findAll(){
        return ResponseEntity.ok().body(serviceListService.findAll());
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceListDTO> findById(@PathVariable Integer id){
        ServiceList obj = this.serviceListService.findById(id);
        return ResponseEntity.ok().body(new ServiceListDTO(obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<ServiceListDTO> create(@Valid @RequestBody ServiceListDTO objDto){
        ServiceList newObj = serviceListService.create(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ServiceListDTO> update(@PathVariable Integer id, @Valid @RequestBody ServiceListDTO objDto){
        ServiceList Obj = serviceListService.update(id, objDto);
        return ResponseEntity.ok().body(new ServiceListDTO(Obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ServiceListDTO> delete(@PathVariable Integer id){
        serviceListService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
