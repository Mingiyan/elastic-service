package ru.halmg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.halmg.api.Elastic;
import ru.halmg.api.ServiceCRUD;
import ru.halmg.model.Person;
import ru.halmg.repository.PersonRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
/*
 * here we do not need operations that can make changes to the database
 */
public class PersonService implements ServiceCRUD<String, Person>, Elastic<Person> {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

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
    public Map<String, Object> toMap(Person person) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", person.getId());
        map.put("name", person.getName());
        map.put("city", person.getCity());
        map.put("birthday", person.getBirthday());
        map.put("departmentId", person.getDepartmentId());
        map.put("positionId", person.getPositionId());
        map.put("hash", person.hashCode());
        return map;
    }
}
