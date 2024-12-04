package com.interno.services;

import com.interno.domains.Animal;
import com.interno.domains.Client;
import com.interno.domains.Employee;
import com.interno.domains.ServiceList;
import com.interno.domains.enums.ServiceType;
import com.interno.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ServiceListRepository serviceListRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void initDB(){

        Animal animal1 = new Animal(1, "dog", "Labrador","leão");

        Animal animal2 = new Animal(2, "cat", "persian", "meow");

        Client client1 = new Client(1, "memphis.depay@gmail.com","123-321-456-12","Memphis",encoder.encode("123"), animal1);

        Client client2 = new Client(3, "lewis.hamilton@gmail.com","123-321-456-10","Lewis",encoder.encode("123"), animal2);

        Employee employee1 = new Employee(2, "leila.pereira@gmail.com", "122-321-456-12", "Leila Pereira", encoder.encode("123"));

        ServiceList serviceList1 = new ServiceList(1, client1, employee1, "Banho", 40.0, ServiceType.BATH);

        animalRepository.save(animal1);
        animalRepository.save(animal2);
        clientRepository.save(client1);
        clientRepository.save(client2);
        employeeRepository.save(employee1);
        serviceListRepository.save(serviceList1);
    }
}
