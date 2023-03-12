package ru.dilgorp.domain.model;

import ru.dilgorp.domain.enums.PayPaymentStatus;

import java.util.UUID;

public record PayPayment(Long id, UUID externalId, PayPaymentStatus status) {
}
