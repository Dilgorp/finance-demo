package ru.dilgorp.domain.message.payload;

import ru.dilgorp.domain.enums.PaymentStatus;

public record StatusChangedPayload(PaymentStatus status) {
}
