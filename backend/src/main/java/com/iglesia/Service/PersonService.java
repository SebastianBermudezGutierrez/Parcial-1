package com.iglesia.Service;

import com.iglesia.Person;
import com.iglesia.Church;
import com.iglesia.PersonRepository;
import com.iglesia.ChurchRepository;
import com.iglesia.dto.PersonDTO;
import com.iglesia.event.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;
    private final ChurchRepository churchRepository;
    private final DomainEventPublisher eventPublisher;

    public PersonService(PersonRepository personRepository, 
                        ChurchRepository churchRepository,
                        DomainEventPublisher eventPublisher) {
        this.personRepository = personRepository;
        this.churchRepository = churchRepository;
        this.eventPublisher = eventPublisher;
    }

    public PersonDTO createPerson(String firstName, String lastName, String document, String phone, String email) {
        Church church = getDefaultChurch();
        
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setDocument(document);
        person.setPhone(phone);
        person.setEmail(email);
        person.setChurch(church);
        
        Person savedPerson = personRepository.save(person);
        
        // Publicar evento de dominio
        eventPublisher.publishPersonCreated(savedPerson);
        
        return PersonDTO.from(savedPerson);
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> getAllPeople() {
        Church church = getDefaultChurch();
        return personRepository.findAllByChurchId(church.getId())
            .stream()
            .map(PersonDTO::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        return PersonDTO.from(person);
    }

    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Persona no encontrada");
        }
        personRepository.deleteById(id);
    }

    private Church getDefaultChurch() {
        return churchRepository.findAll()
            .stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Debe registrar una iglesia primero"));
    }
}
