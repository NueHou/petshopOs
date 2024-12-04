package com.interno.resources;


import com.interno.domains.Client;
import com.interno.domains.dtos.ClientDTO;
import com.interno.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
@Tag(name = "Clients", description = "Api para gerenciamento de clientes")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes.")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        return ResponseEntity.ok().body(clientService.findAll());
    }

    @Operation(summary = "Listar cliente por id", description = "Retorna o clientes pela id.")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id){
        Client obj = this.clientService.findById(id);
        return ResponseEntity.ok().body(new ClientDTO(obj));
    }

    @Operation(summary = "Listar por cpf", description = "Retorna o clientes por cpf.")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CLIENT')")
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<ClientDTO> findByCpf(@PathVariable String cpf){
        Client obj = this.clientService.findByCpf(cpf);
        return ResponseEntity.ok().body(new ClientDTO((obj)));
    }

    @Operation(summary = "Listar o cliente por email", description = "Retorna uma lista com o cliente por email.")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CLIENT')")
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<ClientDTO> findByEmail(@PathVariable String email){
        Client obj = this.clientService.findByEmail(email);
        return ResponseEntity.ok().body(new ClientDTO(obj));
    }

    @Operation(summary = "Cria clietne", description = "Cria um novo cliente")
    @PreAuthorize("hasRole('EMPLOYEE') ")
    @PostMapping
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO objDto, Integer id){
        Client newObj = clientService.create(id, objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @Operation(summary = "Deleta um cliente", description = "Deleta um cliente.")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping
    public ResponseEntity<ClientDTO> delete(@PathVariable Integer id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
