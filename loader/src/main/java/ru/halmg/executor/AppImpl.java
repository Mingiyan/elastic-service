package ru.halmg.executor;

import org.springframework.beans.factory.annotation.Autowired;
import ru.halmg.model.Person;
import ru.halmg.service.ElasticService;
import ru.halmg.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppImpl implements App {

    private ElasticService elasticService;
    private PersonService personService;

    @Autowired
    public AppImpl(ElasticService elasticService, PersonService personService) {
        this.elasticService = elasticService;
        this.personService = personService;
    }

    @Override
    public void start() {
        List<Person> personList = personService.findAll();
        List<String> idListPerson = new ArrayList<>();

        if (!elasticService.indexExists("person")) {
            personList.forEach(person -> elasticService.create(personService.toMap(person), "person", person.getId()));
        } else {
            for (Person person : personList) {
                String hash = elasticService.getHashCode("person", person.getId());
                idListPerson.add(person.getId());
                if (hash == null || person.hashCode() != Integer.valueOf(hash)) {
                    elasticService.create(personService.toMap(person), "person", person.getId());
                }
            }
        }

        List<Map<String, Object>> listFromElastic = elasticService.searchAll("person");
        List<String> idListElastic = listFromElastic.stream()
                .map(map -> map.get("id").toString()).collect(Collectors.toList());

        idListElastic.removeAll(idListPerson);

        idListElastic.forEach(toDelete -> elasticService.delete("person", toDelete));

        elasticService.closeClient();
    }
}
