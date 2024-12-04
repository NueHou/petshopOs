package com.interno.services;

import com.interno.domains.Employee;
import com.interno.domains.dtos.EmployeeDTO;
import com.interno.repositories.EmployeeRepository;
import com.interno.services.exceptions.DataIntegrityViolationException;
import com.interno.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<EmployeeDTO> findAll(){
        return employeeRepository.findAll().stream().map(obj -> new EmployeeDTO(obj)).collect(Collectors.toList());
    }

    public Employee findById(Integer id){
        Optional<Employee> obj = employeeRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+id));
    }

    public Employee findByCpf(String cpf){
        Optional<Employee> obj = employeeRepository.findByCpf(cpf);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! CPF:"+cpf));
    }

    public Employee findByEmail(String email){
        Optional<Employee> obj = employeeRepository.findByEmail(email);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Email:"+email));
    }

    public Employee create(Integer id, EmployeeDTO objDto){
        objDto.setId(id);
        objDto.setPassword(encoder.encode(objDto.getPassword()));
        ValidaPorCPFeEmail(objDto);
        Employee newObj = new Employee(objDto);
        return employeeRepository.save(newObj);
    }

    public Employee update(Integer id, EmployeeDTO objDto){
        objDto.setId(id);
        Employee oldObj = findById(id);
        ValidaPorCPFeEmail(objDto);
        oldObj = new Employee(objDto);
        return employeeRepository.save(oldObj);
    }

    public void delete(Integer id){
        Employee obj = findById(id);
        employeeRepository.deleteById(id);
    }

    private void ValidaPorCPFeEmail(EmployeeDTO objDto) {
        Optional<Employee> obj = employeeRepository.findByCpf(objDto.getCpf());
        if(obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = employeeRepository.findByEmail(objDto.getEmail());
        if(obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }

    }
}
