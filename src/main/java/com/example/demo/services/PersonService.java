package com.example.demo.services;

import com.example.demo.models.Person;
import com.example.demo.models.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {
        System.out.println("Creating person: " + person.getName());
        return personRepository.save(person);
    }

    public List<Person> findAll() {
        System.out.println("Fetching all persons");
        return (List<Person>) personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        System.out.println("Fetching person with ID: " + id);
        return personRepository.findById(id);
    }

    public Person updatePerson(Long id, Person personDetails) {
        System.out.println("Updating person with ID: " + id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id " + id));
        person.setName(personDetails.getName());
        person.setEmail(personDetails.getEmail());
        person.setPassword(personDetails.getPassword());
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        System.out.println("Deleting person with ID: " + id);
        personRepository.deleteById(id);
    }
}