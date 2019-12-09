package ru.halmg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.halmg.api.Elastic;
import ru.halmg.api.ServiceCRUD;
import ru.halmg.model.Person;
import ru.halmg.repository.PersonRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonService implements ServiceCRUD<String, Person>, Elastic<Person> {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // here we do not need operations that can make changes to the database

    @Override
    public void save(Person object) {

    }

    @Override
    public Optional<Person> findById(String id) {
        return personRepository.findById(id);
    }

    @Override
    public Person update(Person object) {
        return null;
    }

    @Override
    public void remove(Person object) {

    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Map<String, Object> toMap(Person object) {
        return null;
    }
}
