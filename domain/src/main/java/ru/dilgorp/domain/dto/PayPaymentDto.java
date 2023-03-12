package ru.dilgorp.domain.dto;

import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.domain.model.PayPayment;

import java.util.UUID;

public record PayPaymentDto(Long id, UUID externalId, PayPaymentStatus status) {
    public static PayPaymentDto from(PayPayment payment) {
        return new PayPaymentDto(payment.id(), payment.externalId(), payment.status());
    }
}
