package ru.halmg.executor;

import org.springframework.beans.factory.annotation.Autowired;
import ru.halmg.model.Person;
import ru.halmg.service.ElasticService;
import ru.halmg.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        if (!elasticService.indexExists("employee")) {
            for (Person person : personList) {
                elasticService.create(personService.toMap(person), "person", person.getId());
            }
        } else {
            for (Person person : personList) {
                String hash = elasticService.getHashCode("person", person.getId());
                idListPerson.add(person.getId());
                if (hash == null || person.hashCode() != Integer.valueOf(hash)) {
                    elasticService.create(personService.toMap(person), "person", person.getId());
                }
            }
        }

        List<Map<String, Object>> listFromElastic = elasticService.searchAll("employee");
        List<String> idListElastic = new ArrayList<>();

        for (Map<String, Object> map : listFromElastic) {
            if (map != null) {
                idListElastic.add(map.get("id").toString());
            }
        }

        idListElastic.removeAll(idListPerson);

        for (String toDelete : idListElastic) {
            elasticService.delete("person", toDelete);
        }

        elasticService.closeClient();
    }
}
