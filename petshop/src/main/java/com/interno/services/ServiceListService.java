package com.interno.services;

import com.interno.domains.ServiceList;
import com.interno.domains.dtos.ServiceListDTO;
import com.interno.repositories.ServiceListRepository;
import com.interno.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceListService {

    @Autowired
    private ServiceListRepository serviceListRepository;

    public List<ServiceListDTO> findAll(){
        return serviceListRepository.findAll().stream().map(obj -> new ServiceListDTO(obj)).collect(Collectors.toList());
    }

    public ServiceList findById(Integer id){
        Optional<ServiceList> obj = serviceListRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+id));
    }

    public ServiceList create(ServiceListDTO objDto){
        objDto.setId(null);
        ServiceList newObj = new ServiceList();
        return serviceListRepository.save(newObj);
    }

    public ServiceList update(Integer id, ServiceListDTO objDto){
        objDto.setId(id);
        ServiceList oldObj = findById(id);
        oldObj = new ServiceList();
        return serviceListRepository.save(oldObj);
    }

    public void delete(Integer id){
        ServiceList obj = findById(id);
        serviceListRepository.deleteById(id);
    }
}
