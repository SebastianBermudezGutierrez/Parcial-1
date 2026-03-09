package com.iglesia.event;

import java.time.LocalDateTime;

public class PersonCreatedEvent {
    private final Long personId;
    private final String firstName;
    private final String lastName;
    private final LocalDateTime createdAt;

    public PersonCreatedEvent(Long personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = LocalDateTime.now();
    }

    public Long getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
