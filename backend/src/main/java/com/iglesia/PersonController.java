package com.iglesia;

import com.iglesia.Service.PersonService;
import com.iglesia.dto.PersonDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public PersonDTO create(@RequestBody PersonRequest request) {
        return personService.createPerson(
            request.firstName(),
            request.lastName(),
            request.document(),
            request.phone(),
            request.email()
        );
    }

    @GetMapping
    public List<PersonDTO> list() {
        return personService.getAllPeople();
    }

    @GetMapping("/{id}")
    public PersonDTO getById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    public record PersonRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        String document,
        String phone,
        String email
    ) {}
}
