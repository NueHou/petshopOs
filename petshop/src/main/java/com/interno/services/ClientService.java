package com.interno.services;

import com.interno.domains.Client;
import com.interno.domains.dtos.ClientDTO;
import com.interno.repositories.ClientRepository;
import com.interno.services.exceptions.DataIntegrityViolationException;
import com.interno.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<ClientDTO> findAll(){
        return clientRepository.findAll().stream().map(obj -> new ClientDTO(obj)).collect(Collectors.toList());
    }

    public Client findById(Integer id){
        Optional<Client> obj = clientRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+id));
    }

    public Client findByCpf(String cpf){
        Optional<Client> obj = clientRepository.findByCpf(cpf);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! CPF:"+cpf));
    }

    public Client findByEmail(String email){
        Optional<Client> obj = clientRepository.findByEmail(email);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Email:"+email));
    }

    public Client create(Integer id, ClientDTO objDto){
        objDto.setId(id);
        objDto.setPassword(encoder.encode(objDto.getPassword()));
        ValidaPorCPFeEmail(objDto);
        Client newObj = new Client(objDto);
        return clientRepository.save(newObj);
    }

    public Client update(Integer id, ClientDTO objDto){
        objDto.setId(id);
        Client oldObj = findById(id);
        ValidaPorCPFeEmail(objDto);
        oldObj = new Client(objDto);
        return clientRepository.save(oldObj);
    }

    public void delete(Integer id){
        Client obj = findById(id);
        clientRepository.deleteById(id);
    }

    private void ValidaPorCPFeEmail(ClientDTO objDto) {
        Optional<Client> obj = clientRepository.findByCpf(objDto.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = clientRepository.findByEmail(objDto.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }

    }
}
