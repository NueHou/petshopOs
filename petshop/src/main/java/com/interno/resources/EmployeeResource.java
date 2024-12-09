package com.interno.resources;

import com.interno.domains.Client;
import com.interno.domains.Employee;
import com.interno.domains.dtos.ClientDTO;
import com.interno.domains.dtos.EmployeeDTO;
import com.interno.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll(){
        return ResponseEntity.ok().body(employeeService.findAll());
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Integer id){
        Employee obj = this.employeeService.findById(id);
        return ResponseEntity.ok().body(new EmployeeDTO(obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<EmployeeDTO> findByCpf(@PathVariable String cpf){
        Employee obj = this.employeeService.findByCpf(cpf);
        return ResponseEntity.ok().body(new EmployeeDTO((obj)));
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<EmployeeDTO> findByEmail(@PathVariable String email){
        Employee obj = this.employeeService.findByEmail(email);
        return ResponseEntity.ok().body(new EmployeeDTO(obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO objDto, Integer id){
        Employee newObj = employeeService.create(objDto, id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Integer id, @Valid @RequestBody EmployeeDTO objDto){
        Employee Obj = employeeService.update(id, objDto);
        return ResponseEntity.ok().body(new EmployeeDTO(Obj));
    }

    @PreAuthorize("hasRole('EMPLOYEE') ")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> delete(@PathVariable Integer id){
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
