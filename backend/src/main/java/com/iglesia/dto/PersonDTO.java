package com.iglesia.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonDTO(
    Long id,
    @NotBlank String firstName,
    @NotBlank String lastName,
    String document,
    String phone,
    String email
) {
    public static PersonDTO from(com.iglesia.Person person) {
        return new PersonDTO(
            person.getId(),
            person.getFirstName(),
            person.getLastName(),
            person.getDocument(),
            person.getPhone(),
            person.getEmail()
        );
    }
}
