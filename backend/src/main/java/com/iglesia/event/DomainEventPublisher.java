package com.iglesia.event;

import com.iglesia.Person;
import com.iglesia.Payment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public DomainEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishPersonCreated(Person person) {
        PersonCreatedEvent event = new PersonCreatedEvent(
            person.getId(),
            person.getFirstName(),
            person.getLastName()
        );
        eventPublisher.publishEvent(event);
    }

    public void publishPaymentProcessed(Payment payment) {
        PaymentProcessedEvent event = new PaymentProcessedEvent(
            payment.getId(),
            payment.getType(),
            payment.getAmount(),
            payment.getStatus()
        );
        eventPublisher.publishEvent(event);
    }
}
