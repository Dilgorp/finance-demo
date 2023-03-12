package ru.dilgorp.domain.model;

import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;

import java.time.LocalDateTime;
import java.util.UUID;

public record Payment(
        Long id,
        Long dealId,
        Long seq,
        Service service,
        PaymentStatus status,
        LocalDateTime date,
        UUID externalId) {
}
