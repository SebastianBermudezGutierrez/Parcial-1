package com.iglesia.repository;

import com.iglesia.Person;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Person save(Person person);
    Optional<Person> findById(Long id);
    List<Person> findAllByChurchId(Long churchId);
    void deleteById(Long id);
    boolean existsById(Long id);
}
