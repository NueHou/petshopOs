package com.interno.services;

import com.interno.domains.Animal;
import com.interno.domains.dtos.AnimalDTO;
import com.interno.repositories.AnimalRepository;
import com.interno.services.exceptions.DataIntegrityViolationException;
import com.interno.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<AnimalDTO> findAll(){
        return animalRepository.findAll().stream().map(obj -> new AnimalDTO(obj)).collect(Collectors.toList());
    }

    public Animal findById(Integer id){
        Optional<Animal> obj = animalRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+id));
    }

    public Animal findByName(String name){
        Optional<Animal> obj = animalRepository.findByName(name);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Nome:"+ name));
    }

    public Animal create(AnimalDTO objDto){
        objDto.setId(null);
        ValidaPorName(objDto);
        Animal newObj = new Animal();
        return animalRepository.save(newObj);
    }

    public Animal update(Integer id, AnimalDTO objDto){
        objDto.setId(id);
        Animal oldObj = findById(id);
        ValidaPorName(objDto);
        oldObj = new Animal();
        return animalRepository.save(oldObj);
    }

    public void delete(Integer id){
        Animal obj = findById(id);
        animalRepository.deleteById(id);
    }
    private void ValidaPorName(AnimalDTO objDto) {
        Optional<Animal> obj = animalRepository.findByName(objDto.getName());
        if(obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

    }
}
