package ru.halmg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.halmg.model.Person;

public interface PersonRepository extends JpaRepository<Person, String> {
}
